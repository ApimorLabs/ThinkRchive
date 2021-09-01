package work.racka.thinkrchive.ui.main.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import work.racka.thinkrchive.data.dataTransferObjects.asThinkpad
import work.racka.thinkrchive.repository.ThinkpadRepository
import work.racka.thinkrchive.ui.main.screenStates.ThinkpadDetailsScreenState
import javax.inject.Inject

@HiltViewModel
class ThinkpadDetailsViewModel @Inject constructor(
    private val thinkpadRepository: ThinkpadRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val thinkpadName = savedStateHandle.get<String>("thinkpad")!!

    private val _uiState = MutableStateFlow<ThinkpadDetailsScreenState>(
        value = ThinkpadDetailsScreenState.EmptyState
    )
    val uiState: StateFlow<ThinkpadDetailsScreenState> = _uiState

    init {
        initializeThinkpad()
    }

    private fun initializeThinkpad() {
        viewModelScope.launch {
            thinkpadRepository.getThinkpad(thinkpadName).collect {
                _uiState.value = ThinkpadDetailsScreenState.ThinkpadDetail(it.asThinkpad())
            }
        }
    }


}
