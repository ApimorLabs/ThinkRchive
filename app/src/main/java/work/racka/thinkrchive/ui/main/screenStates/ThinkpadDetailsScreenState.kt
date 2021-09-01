package work.racka.thinkrchive.ui.main.screenStates

import work.racka.thinkrchive.data.model.Thinkpad

sealed class ThinkpadDetailsScreenState {
    data class ThinkpadDetail(val thinkpad: Thinkpad) : ThinkpadDetailsScreenState()
    object EmptyState : ThinkpadDetailsScreenState()
}