package work.racka.thinkrchive.billing

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LifecycleObserver
import com.android.billingclient.api.*
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class BillingManager @Inject constructor(
    @ActivityContext private val context: Context
) : LifecycleObserver, PurchasesUpdatedListener, AcknowledgePurchaseResponseListener {

    private val billingClient = BillingClient.newBuilder(context)
        .setListener(this)
        .enablePendingPurchases()
        .build()

    init {
        startConnection()
    }

    private val _billingResponse =
        MutableStateFlow(BillingClient.BillingResponseCode.SERVICE_DISCONNECTED)
    val billingResponse: StateFlow<Int>
        get() = _billingResponse

    private val _purchases = MutableStateFlow<List<Purchase>?>(null)
    val purchases: SharedFlow<List<Purchase>?>
        get() = _purchases

    /**
     * BillingManager Listeners implementations
     * onPurchasesUpdated() is called when a purchase is done.
     * You then handle the purchase as needed.
     *
     * onAcknowledgePurchaseResponse() is called when a one-time non-consumable is acknowledged
     * You can get the BillingResult from here and use to get responseCode or debugMessage
     */
    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        _billingResponse.value = billingResult.responseCode
        Timber.d("onPurchasesUpdated() response: ${billingResult.responseCode}")
        Timber.d("userCancelled: ${billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED}")
        _purchases.value = purchases
    }

    override fun onAcknowledgePurchaseResponse(billingResult: BillingResult) {
        // Get your responseCode
        _billingResponse.value = billingResult.responseCode
    }

    fun startConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                _billingResponse.value = BillingClient.BillingResponseCode.SERVICE_DISCONNECTED
                Timber.i("Billing Service Disconnected")
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                _billingResponse.value = billingResult.responseCode
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    Timber.i("Billing Setup successful")
                }
            }
        })
    }

    fun endConnection() {
        billingClient.endConnection()
    }

    suspend fun querySkuDetails(): SkuDetailsResult {
        val sku = Skus.DonateSku
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(sku.skuList)
            .setType(sku.skuType)

        billingClient.querySkuDetailsAsync(
            params.build()
        ) { billingResult, skuList ->
            Timber.i("onSkuDetailsResponse: ${billingResult.responseCode}")
            if (skuList != null) {
                for (skuDetail in skuList) {
                    Timber.i(skuDetail.toString())
                }
            } else {
                Timber.i("No SKUs found")
            }
        }
        // leverage querySkuDetails Kotlin extension function
        return withContext(Dispatchers.IO) {
            billingClient.querySkuDetails(params.build())
        }
    }

    fun launchPurchaseScreen(activity: Activity, skuDetails: SkuDetails) {
        val flowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetails)
            .build()

        val responseCode = billingClient.launchBillingFlow(activity, flowParams).responseCode
        _billingResponse.value = responseCode
        Timber.d("Billing Flow Response Code: $responseCode")
    }

    /**
     * Should be called whenever billingManager is created to make sure there are no
     * pending purchases done by the user previously.
     * Should be called in onResume and onCreate
     */
    suspend fun refreshPurchases() {
        Timber.d("Refreshing purchases.")
        val purchasesResult = billingClient.queryPurchasesAsync(BillingClient.SkuType.INAPP)
        val billingResult = purchasesResult.billingResult
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            consumeConsumable(purchasesResult.purchasesList)
        } else {
            Timber.e("Problem getting purchases: ${billingResult.debugMessage}")
        }
    }

    /**
     * Consuming the purchase with proper checks
     * Here you can check for entitlement of previous purchase or grant entitlement
     *
     */
    suspend fun consumeConsumable(purchases: List<Purchase>?) {
        if (billingResponse.value == BillingClient.BillingResponseCode.OK) {
            purchases?.let {
                Timber.i("Purchase List Not Null: $it")
                for (purchase in it) {
                    val token = handleConsumablePurchase(purchase)
                    // Store the token locally or in your server
                    // You can grant entitlement here
                    Timber.i("token is: $token")
                }
            }
        } else if (billingResponse.value == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle cancelled purchases
            // Don't grant entitlement
            Timber.i("Purchase cancelled")
        }
    }

    /**
     * For internal use only. This only checks if the purchase has been made and consumes it
     * It should then be used to consume all purchases as required in another function
     * It does not grant entitlement or check for entitlement of the purchase
     * A purchaseToken is returned which can be used to check for entitlement.
     * If the token returned is null it means that the purchase was not completed or the
     * user never did this purchase
     */
    private suspend fun handleConsumablePurchase(purchase: Purchase): String? {
        var token: String? = null
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            val consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()
            val consumeResult = withContext(Dispatchers.IO) {
                billingClient.consumePurchase(consumeParams)
            }
            _billingResponse.value = consumeResult.billingResult.responseCode
            token = consumeResult.purchaseToken
            Timber.i("Purchase Token: $token")
        }
        return token
    }

    suspend fun handleNonConsumablePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
                val ackPurchaseResult = withContext(Dispatchers.IO) {
                    billingClient.acknowledgePurchase(acknowledgePurchaseParams)
                }
            } else {
                Timber.i("Item already acknowledged ")
            }
        } else {
            Timber.i("Item not purchased")
        }
    }
}