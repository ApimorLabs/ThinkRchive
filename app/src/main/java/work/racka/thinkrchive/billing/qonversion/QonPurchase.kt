package work.racka.thinkrchive.billing.qonversion

import android.app.Activity
import android.widget.Toast
import com.qonversion.android.sdk.Qonversion
import com.qonversion.android.sdk.QonversionError
import com.qonversion.android.sdk.QonversionPermissionsCallback
import com.qonversion.android.sdk.dto.QPermission
import com.qonversion.android.sdk.dto.offerings.QOffering
import work.racka.thinkrchive.ui.main.viewModel.QonversionViewModel

fun qonPurchase(
    activity: Activity,
    offering: QOffering,
    qonViewModel: QonversionViewModel
) {
    Qonversion.purchase(
        activity,
        offering.offeringID,
        object : QonversionPermissionsCallback{
            override fun onError(error: QonversionError) {
                Toast.makeText(
                    activity,
                    "Purchase Failed: ${error.additionalMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onSuccess(permissions: Map<String, QPermission>) {
                Toast.makeText(
                    activity,
                    "Purchase Successful for ${offering.offeringID}",
                    Toast.LENGTH_SHORT
                ).show()

                qonViewModel.updatePermissions()
            }
        }
    )
}