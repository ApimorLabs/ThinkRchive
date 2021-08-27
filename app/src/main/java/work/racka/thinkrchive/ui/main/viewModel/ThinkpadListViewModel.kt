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
import work.racka.thinkrchive.utils.Sort
import work.racka.thinkrchive.utils.getChipNamesList
import javax.inject.Inject

@HiltViewModel
class ThinkpadListViewModel @Inject constructor(
    private val thinkpadRepository: ThinkpadRepository
) : ViewModel() {

    private val allThinkpads = MutableStateFlow<List<Thinkpad>>(listOf())
    private val availableThinkpadSeries = MutableStateFlow<List<String>>(listOf())
    private val networkLoading = MutableStateFlow(false)
    private val networkError = MutableStateFlow("")
    private val sortOption = MutableStateFlow(Sort.ALPHABETICAL_ASC)

    // This will combine the different Flows emitted in this ViewModel into a single state
    // that will be observed by the UI.
    // Compose mutableStateOf can also be used to provide something similar
    val uiState: StateFlow<ThinkpadListScreenState> = combine(
        allThinkpads,
        networkLoading,
        networkError,
        availableThinkpadSeries,
        sortOption
    ) { allThinkpads, networkLoading, networkError, availableThinkpadSeries, sortOption ->
        ThinkpadListScreenState(
            thinkpadList = allThinkpads,
            networkLoading = networkLoading,
            networkError = networkError,
            thinkpadSeriesList = availableThinkpadSeries,
            sortOption = sortOption
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
            when (sortOption.value) {
                Sort.ALPHABETICAL_ASC -> {
                    thinkpadRepository.getThinkpadsAlphaAscending(query)
                        .collect {
                            allThinkpads.value = it.asDomainModel()
                            if (allThinkpads.value.size > availableThinkpadSeries.value.size) {
                                availableThinkpadSeries.value = allThinkpads.value.getChipNamesList()
                            }
                        }
                }
                Sort.NEW_RELEASE_FIRST -> {
                    thinkpadRepository.getThinkpadsNewestFirst(query)
                        .collect {
                            allThinkpads.value = it.asDomainModel()
                        }
                }
                Sort.OLD_RELEASE_FIRST -> {
                    thinkpadRepository.getThinkpadsOldestFirst(query)
                        .collect {
                            allThinkpads.value = it.asDomainModel()
                        }
                }
                Sort.LOW_PRICE_FIRST -> {
                    thinkpadRepository.getThinkpadsLowPriceFirst(query)
                        .collect {
                            allThinkpads.value = it.asDomainModel()
                        }
                }
                Sort.HIGH_PRICE_FIRST -> {
                    thinkpadRepository.getThinkpadsHighPriceFirst(query)
                        .collect {
                            allThinkpads.value = it.asDomainModel()
                        }
                }
            }
        }
    }

    // Set the sort option from the UI
    fun sortSelected(sort: Sort) {
        sortOption.value = sort
    }
}