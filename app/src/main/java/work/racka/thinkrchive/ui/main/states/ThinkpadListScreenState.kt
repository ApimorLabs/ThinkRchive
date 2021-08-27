package work.racka.thinkrchive.ui.main.states

import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.utils.Sort

data class ThinkpadListScreenState(
    val thinkpadList: List<Thinkpad> = listOf(),
    val networkLoading: Boolean = false,
    val networkError: String = "",
    val thinkpadSeriesList: List<String> = listOf(),
    var sortOption: Sort = Sort.ALPHABETICAL_ASC
) {
    companion object {
        val Empty = ThinkpadListScreenState()
    }
}
