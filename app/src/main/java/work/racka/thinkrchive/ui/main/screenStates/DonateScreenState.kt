package work.racka.thinkrchive.ui.main.screenStates

import com.android.billingclient.api.SkuDetails

sealed class DonateScreenState {
    data class Donate(
        val skuDetailsList: List<SkuDetails> = listOf()
    ): DonateScreenState()

    companion object {
        val DefaultState = Donate()
    }
}
