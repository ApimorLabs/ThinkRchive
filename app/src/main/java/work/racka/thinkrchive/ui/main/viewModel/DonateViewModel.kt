package work.racka.thinkrchive.ui.main.viewModel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.SkuDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import work.racka.thinkrchive.repository.BillingRepository
import work.racka.thinkrchive.ui.main.screenStates.DonateScreenState
import javax.inject.Inject

@HiltViewModel
class DonateViewModel @Inject constructor(
    private val billingRepository: BillingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DonateScreenState>(
        value = DonateScreenState.DefaultState
    )
    val uiState: StateFlow<DonateScreenState>
        get() = _uiState

    init {
        initialize()
        Timber.i("DonateViewModel created")
    }

    private fun initialize() {
        viewModelScope.launch {
            billingRepository.billingResponse.collect { billingResponse ->
                if (billingResponse == BillingClient.BillingResponseCode.OK) {
                    val skuDetailsList = billingRepository.querySkuDetails().skuDetailsList
                    skuDetailsList?.let {
                        _uiState.value = DonateScreenState.Donate(it)
                    }
                }
            }

            billingRepository.purchasesState.collect {
                billingRepository.consumePurchase()
            }
        }
    }

    fun launchPurchaseScreen(activity: Activity, sku: SkuDetails) {
        billingRepository.launchPurchaseScreen(activity, sku)
    }
}