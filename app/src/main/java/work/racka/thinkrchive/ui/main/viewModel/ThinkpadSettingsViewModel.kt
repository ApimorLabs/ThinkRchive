package work.racka.thinkrchive.ui.main.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import work.racka.thinkrchive.repository.DataStoreRepository
import work.racka.thinkrchive.ui.main.states.ThinkpadSettingsScreenState
import javax.inject.Inject

@HiltViewModel
class ThinkpadSettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ThinkpadSettingsScreenState>(
        value = ThinkpadSettingsScreenState.DefaultState
    )
    private val themeOptionValue = MutableStateFlow(0)

    val uiState: StateFlow<ThinkpadSettingsScreenState> = combine(
        themeOptionValue
    ) { themeOptionValue ->
        ThinkpadSettingsScreenState.ThinkpadSettings(
            themeOption = themeOptionValue.first()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ThinkpadSettingsScreenState.DefaultState
    )

    init {
        readSettings()
    }

    fun saveThemeSetting(themeValue: Int) {
        viewModelScope.launch {
            dataStoreRepository.saveThemeSetting(value = themeValue)
        }
    }

    private fun readSettings() {
        viewModelScope.launch {
            dataStoreRepository.readThemeSetting.collect { themeValue ->
                _uiState.value = ThinkpadSettingsScreenState.ThinkpadSettings(themeValue)
            }
        }
    }
}