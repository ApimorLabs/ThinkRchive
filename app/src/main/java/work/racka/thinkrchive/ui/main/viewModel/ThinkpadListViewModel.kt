package work.racka.thinkrchive.ui.main.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import work.racka.thinkrchive.data.dataTransferObjects.asDomainModel
import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.repository.ThinkpadRepository
import work.racka.thinkrchive.utils.Resource
import javax.inject.Inject

@HiltViewModel
class ThinkpadListViewModel @Inject constructor(
    private val thinkpadRepository: ThinkpadRepository
) : ViewModel() {

    var thinkpadList by mutableStateOf<List<Thinkpad>>(listOf())
    var loadError by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    init {
        loadThinkpadList()
    }

    fun loadThinkpadList() {
        viewModelScope.launch {
            isLoading = true
            Timber.d("loading ThinkpadList")
            when (val result = thinkpadRepository.getAllThinkpads()) {
                is Resource.Success -> {
                    thinkpadList = result.data!!
                    isLoading = true
                }
                is Resource.Error -> {
                    loadError = result.message!!
                    isLoading = false
                }
                else -> { }
            }
        }
    }
}