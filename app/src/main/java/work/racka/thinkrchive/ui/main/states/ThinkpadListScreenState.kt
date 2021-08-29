package work.racka.thinkrchive.ui.main.states

import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.utils.Sort

sealed class ThinkpadListScreenState {
    data class ThinkpadListScreen(
        val thinkpadList: List<Thinkpad> = listOf(),
        val networkLoading: Boolean = false,
        val networkError: String = "",
        val thinkpadSeriesList: List<String> = listOf(),
        var sortOption: Sort = Sort.ALPHABETICAL_ASC
    ) : ThinkpadListScreenState()
    companion object {
        val Empty = ThinkpadListScreen()
    }
}
