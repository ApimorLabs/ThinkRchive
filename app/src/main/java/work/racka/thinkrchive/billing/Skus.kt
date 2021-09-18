package work.racka.thinkrchive.billing

import com.android.billingclient.api.BillingClient

enum class Skus(
    val skuType: String,
    val skuList: ArrayList<String>
) {
    DonateSku(
        skuType = BillingClient.SkuType.INAPP,
        skuList = arrayListOf("test_prod_1", "test_prod_2")
    )
}