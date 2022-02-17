package work.racka.thinkrchive.ui.main.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.qonversion.android.sdk.Qonversion
import com.qonversion.android.sdk.QonversionError
import com.qonversion.android.sdk.QonversionOfferingsCallback
import com.qonversion.android.sdk.QonversionPermissionsCallback
import com.qonversion.android.sdk.dto.QPermission
import com.qonversion.android.sdk.dto.offerings.QOffering
import com.qonversion.android.sdk.dto.offerings.QOfferings
import work.racka.thinkrchive.billing.Subscription

class QonversionViewModel : ViewModel() {

    var offerings by mutableStateOf<List<QOffering>>(emptyList())
        private set

    var hasPremium by mutableStateOf(false)
        private set

    init {
        loadOfferings()
        updatePermissions()
    }

    private fun loadOfferings() {
        Qonversion.offerings(object : QonversionOfferingsCallback {
            override fun onError(error: QonversionError) {
                // You can handle errors here
            }

            override fun onSuccess(offerings: QOfferings) {
                this@QonversionViewModel.offerings = offerings.availableOfferings
            }
        })
    }

    fun updatePermissions() {
        Qonversion.checkPermissions(object : QonversionPermissionsCallback {
            override fun onError(error: QonversionError) {
                // You can handle the error here
            }

            override fun onSuccess(permissions: Map<String, QPermission>) {
                hasPremium = permissions[Subscription.Premium.id]?.isActive() == true
            }

        })
    }


}