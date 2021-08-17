package work.racka.thinkrchive.ui.main.states

import work.racka.thinkrchive.data.model.Thinkpad

data class ThinkpadListScreenState(
    val thinkpadList: List<Thinkpad> = listOf(),
    val networkLoading: Boolean = false,
    val networkError: String = "",
    val thinkpadSeriesList: List<String> = listOf()
) {
    companion object {
        val Empty = ThinkpadListScreenState()
    }
}
