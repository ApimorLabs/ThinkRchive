package work.racka.thinkrchive.ui.main.states

sealed class ThinkpadSettingsScreenState {
    data class ThinkpadSettings(
        val themeOption: Int = 0
    ): ThinkpadSettingsScreenState()

    companion object {
        val DefaultState = ThinkpadSettings()
    }
}