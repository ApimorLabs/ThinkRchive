package work.racka.thinkrchive.ui.main.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import work.racka.thinkrchive.data.dataTransferObjects.asDomainModel
import work.racka.thinkrchive.domain.model.Thinkpad
import work.racka.thinkrchive.data.local.dataStore.PrefDataStore
import work.racka.thinkrchive.repository.ThinkpadRepository
import work.racka.thinkrchive.ui.main.screenStates.ThinkpadListScreenState
import work.racka.thinkrchive.utils.Resource
import work.racka.thinkrchive.utils.getChipNamesList
import javax.inject.Inject

@HiltViewModel
class ThinkpadListViewModel @Inject constructor(
    private val thinkpadRepository: ThinkpadRepository,
    private val prefDataStore: PrefDataStore
) : ViewModel() {

    private val allThinkpads = MutableStateFlow<List<Thinkpad>>(listOf())
    private val availableThinkpadSeries = MutableStateFlow<List<String>>(listOf())
    private val networkLoading = MutableStateFlow(false)
    private val networkError = MutableStateFlow("")
    private val sortOption = MutableStateFlow(0)

    // This will combine the different Flows emitted in this ViewModel into a single state
    // that will be observed by the UI.
    // Compose mutableStateOf can also be used to provide something similar
    // But this approach ensures that it will work with regular Android Views OOB
    val uiState: StateFlow<ThinkpadListScreenState> = combine(
        allThinkpads,
        networkLoading,
        networkError,
        availableThinkpadSeries,
        sortOption
    ) { allThinkpads, networkLoading, networkError, availableThinkpadSeries, sortOption ->
        ThinkpadListScreenState.ThinkpadListScreen(
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
        getUserSortOption()
        //getNewThinkpadListFromDatabase()
    }

    // Retrieves new data from the network and inserts it into the database
    // Also used by pull down to refresh.
    fun refreshThinkpadList() {
        viewModelScope.launch {
            networkLoading.value = true
            val result = try {
                val response = thinkpadRepository.getAllThinkpadsFromNetwork()
                networkLoading.value = false
                Resource.Success(data = response)
            } catch (e: Exception) {
                networkLoading.value = false
                Resource.Error(message = "An error occurred: ${e.message}")
            }

            Timber.d("loading ThinkpadList")
            when (result) {
                is Resource.Success -> {
                    thinkpadRepository.refreshThinkpadList(result.data!!)
                }
                is Resource.Error -> {
                    networkError.value = result.message!!
                }
            }
        }
    }

    // Get the user's preferred Sorting option first the load data from the database
    private fun getUserSortOption() {
        viewModelScope.launch {
            prefDataStore.readSortOptionSetting.collect { sortValue ->
                sortOption.value = sortValue
                getNewThinkpadListFromDatabase()
            }
        }
    }

    // Makes sure fresh data is taken from the database even after a network refresh
    // Also takes search query and returns only the data needed
    fun getNewThinkpadListFromDatabase(query: String = "") {
        viewModelScope.launch {
            when (sortOption.value) {
                0 -> {
                    thinkpadRepository.getThinkpadsAlphaAscending(query)
                        .collect {
                            allThinkpads.value = it.asDomainModel()
                            if (
                                allThinkpads.value.size > availableThinkpadSeries.value.size
                                || allThinkpads.value.isEmpty()
                            ) {
                                availableThinkpadSeries.value =
                                    allThinkpads.value.getChipNamesList()
                            }
                        }
                }
                1 -> {
                    thinkpadRepository.getThinkpadsNewestFirst(query)
                        .collect {
                            allThinkpads.value = it.asDomainModel()
                        }
                }
                2 -> {
                    thinkpadRepository.getThinkpadsOldestFirst(query)
                        .collect {
                            allThinkpads.value = it.asDomainModel()
                        }
                }
                3 -> {
                    thinkpadRepository.getThinkpadsLowPriceFirst(query)
                        .collect {
                            allThinkpads.value = it.asDomainModel()
                        }
                }
                4 -> {
                    thinkpadRepository.getThinkpadsHighPriceFirst(query)
                        .collect {
                            allThinkpads.value = it.asDomainModel()
                        }
                }
            }
        }
    }

    // Set the sort option from the UI
    fun sortSelected(sort: Int) {
        sortOption.value = sort
        getNewThinkpadListFromDatabase()
    }
}