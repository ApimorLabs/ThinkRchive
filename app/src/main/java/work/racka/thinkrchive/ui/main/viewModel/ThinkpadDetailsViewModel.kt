package work.racka.thinkrchive.ui.main.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import work.racka.thinkrchive.data.dataTransferObjects.asThinkpad
import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.repository.ThinkpadRepository
import work.racka.thinkrchive.utils.emptyThinkpad
import javax.inject.Inject

@HiltViewModel
class ThinkpadDetailsViewModel @Inject constructor(
    private val thinkpadRepository: ThinkpadRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val thinkpadName = savedStateHandle.get<String>("thinkpad")!!
    val thinkpad = thinkpadRepository.getThinkpad(thinkpadName)

//    init {
//        initializeThinkpad()
//    }
//    private fun initializeThinkpad() {
//        viewModelScope.launch {
//            thinkpadRepository.getThinkpad(thinkpadName).collect {
//                thinkpad = it.asThinkpad()
//            }
//        }
//    }
}