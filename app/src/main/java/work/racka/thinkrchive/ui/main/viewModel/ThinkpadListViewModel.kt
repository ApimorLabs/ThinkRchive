package work.racka.thinkrchive.ui.main.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import work.racka.thinkrchive.data.dataTransferObjects.asDomainModel
import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.repository.ThinkpadRepository
import work.racka.thinkrchive.ui.main.states.ThinkpadListScreenState
import work.racka.thinkrchive.utils.Resource
import work.racka.thinkrchive.utils.getChipNamesList
import javax.inject.Inject

@HiltViewModel
class ThinkpadListViewModel @Inject constructor(
    private val thinkpadRepository: ThinkpadRepository
) : ViewModel() {

    private var allThinkpads = MutableStateFlow<List<Thinkpad>>(listOf())
    private var availableThinkpadSeries = MutableStateFlow<List<String>>(listOf())
    private var networkLoading = MutableStateFlow(false)
    private var networkError = MutableStateFlow("")

    // This will combine the different Flows emitted in this ViewModel into a single state
    // that will be observed by the UI.
    // Compose mutableStateOf can also be used to provide something similar
    val uiState: StateFlow<ThinkpadListScreenState> = combine(
        allThinkpads,
        networkLoading,
        networkError,
        availableThinkpadSeries
    ) { allThinkpads, networkLoading, networkError, availableThinkpadSeries ->
        ThinkpadListScreenState(
            thinkpadList = allThinkpads,
            networkLoading = networkLoading,
            networkError = networkError,
            thinkpadSeriesList = availableThinkpadSeries
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ThinkpadListScreenState.Empty
    )

    init {
        refreshThinkpadList()
        getNewThinkpadListFromDatabase()
    }

    // Retrieves new data from the network and inserts it into the database
    // Also used by pull down to refresh.
    fun refreshThinkpadList() {
        viewModelScope.launch {
            networkLoading.value = true

            Timber.d("loading ThinkpadList")
            when (val result = thinkpadRepository.getAllThinkpadsFromNetwork()) {
                is Resource.Success -> {
                    thinkpadRepository.refreshThinkpadList(result.data!!)
                    networkLoading.value = false
                }
                is Resource.Error -> {
                    networkLoading.value = false
                    networkError.value = result.message!!
                }
            }
        }
    }

    // Makes sure fresh data is taken from the database even after a network refresh
    // Also takes search query and returns only the data needed
    fun getNewThinkpadListFromDatabase(query: String = "") {
        viewModelScope.launch {
            thinkpadRepository.queryThinkpads(query)
                .collect {
                    allThinkpads.value = it.asDomainModel()
                    if (allThinkpads.value.size > availableThinkpadSeries.value.size) {
                        availableThinkpadSeries.value = allThinkpads.value.getChipNamesList()
                    }
                }
        }
    }
}