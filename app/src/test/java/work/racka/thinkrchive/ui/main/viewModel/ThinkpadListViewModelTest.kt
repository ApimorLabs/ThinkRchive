package work.racka.thinkrchive.ui.main.viewModel

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.clearInvocations
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import work.racka.thinkrchive.data.dataTransferObjects.asDatabaseModel
import work.racka.thinkrchive.data.dataTransferObjects.asDomainModel
import work.racka.thinkrchive.data.local.dataStore.PrefDataStore
import work.racka.thinkrchive.domain.model.Thinkpad
import work.racka.thinkrchive.repository.ThinkpadRepository
import work.racka.thinkrchive.testUtils.FakeThinkpadData
import work.racka.thinkrchive.testUtils.MainCoroutineRule
import work.racka.thinkrchive.ui.main.screenStates.ThinkpadListScreenState
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class ThinkpadListViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var thinkpadRepo: ThinkpadRepository
    private lateinit var prefDataStoreRepo: PrefDataStore
    private lateinit var viewModel: ThinkpadListViewModel

    private val expectedThinkpadList = FakeThinkpadData.fakeResponseList
        .asDatabaseModel()
        .toList()
        .asDomainModel()
    private val expectedThinkpadDbList = FakeThinkpadData.fakeResponseList
        .asDatabaseModel()
        .toList()

    @Before
    fun setUp() {
        thinkpadRepo = mock()
        prefDataStoreRepo = mock()
        whenever(prefDataStoreRepo.readSortOptionSetting)
            .thenReturn(flowOf(0))
        whenever(thinkpadRepo.getThinkpadsAlphaAscending(""))
            .thenReturn(flowOf(expectedThinkpadDbList))
        viewModel = ThinkpadListViewModel(thinkpadRepo, prefDataStoreRepo)
    }

    @After
    fun tearDown() {
        clearInvocations(thinkpadRepo, prefDataStoreRepo)
    }

    @Test
    fun uIState_GetsLatestThinkpadListScreenUiState() {
        val query = ""
        val expected = ThinkpadListScreenState.ThinkpadListScreen()
        coroutineRule.runBlockingTest {
            whenever(thinkpadRepo.getAllThinkpadsFromNetwork())
                .thenReturn(listOf())
            whenever(prefDataStoreRepo.readSortOptionSetting)
                .thenReturn(flowOf(0))
            whenever(thinkpadRepo.getThinkpadsAlphaAscending(query))
                .thenReturn(flowOf(listOf()))
            viewModel.refreshThinkpadList()
            viewModel.getNewThinkpadListFromDatabase(query)
            val state = viewModel.uiState
            state.test {
                val actual = expectMostRecentItem()
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun refreshThinkpadList_WhenRefreshed_GetNewList() {
        val query = ""
        val notExpected = expectedThinkpadList
        coroutineRule.runBlockingTest {
            val refreshedList = expectedThinkpadDbList.subList(0, 2)
            whenever(thinkpadRepo.getAllThinkpadsFromNetwork())
                .thenReturn(
                    FakeThinkpadData.fakeResponseList
                        .subList(0, 2)
                )
            whenever(thinkpadRepo.getThinkpadsAlphaAscending(query))
                .thenReturn(flowOf(refreshedList))
            verify(thinkpadRepo)
                .getAllThinkpadsFromNetwork()
            viewModel.refreshThinkpadList()
            viewModel.getNewThinkpadListFromDatabase(query)
            val state = viewModel.uiState
            state.test {
                val actual = expectMostRecentItem() as ThinkpadListScreenState.ThinkpadListScreen
                assertNotEquals(notExpected, actual.thinkpadList)
                // If refreshThinkpadList() is not called networkLoading will be true since there
                // will be no result retrieved from network & the when statement will not be executed
                // inside the refreshThinkpads().
                // getAllThinkpadsFromNetwork() will never be called
                assertFalse(actual.networkLoading)
            }
        }
    }

    @Test
    fun getNewThinkpadListFromDatabase_WhenCalled_DisplayList() {
        val query = ""
        val expected = expectedThinkpadList
        coroutineRule.runBlockingTest {
            val insertFlow = flowOf(expectedThinkpadDbList)
            whenever(thinkpadRepo.getThinkpadsAlphaAscending(query))
                .thenReturn(insertFlow)
            whenever(prefDataStoreRepo.readSortOptionSetting)
                .thenReturn(flowOf(0))
            viewModel.getNewThinkpadListFromDatabase(query)
            val state = viewModel.uiState
            state.test {
                val actual = expectMostRecentItem() as ThinkpadListScreenState.ThinkpadListScreen
                assertEquals(expected, actual.thinkpadList)
            }
        }

    }

    @Test
    fun getNewThinkpadListFromDatabase_WhenSearchQueryFound_DisplayListWithSingleElement() {
        val query = "Thinkpad T450"
        val expected = expectedThinkpadList.subList(1, expectedThinkpadList.lastIndex)
        coroutineRule.runBlockingTest {
            val firstItem = expectedThinkpadDbList.subList(1, expectedThinkpadDbList.lastIndex)
            whenever(thinkpadRepo.getThinkpadsAlphaAscending(query))
                .thenReturn(flowOf(firstItem))
            whenever(prefDataStoreRepo.readSortOptionSetting)
                .thenReturn(flowOf(0))
            viewModel.getNewThinkpadListFromDatabase(query)
            val state = viewModel.uiState
            state.test {
                val actual = expectMostRecentItem() as ThinkpadListScreenState.ThinkpadListScreen
                assertTrue(actual.thinkpadList.isNotEmpty())
                assertEquals(expected, actual.thinkpadList)
            }
        }
    }

    @Test
    fun getNewThinkpadListFromDatabase_WhenSearchQueryNotFound_DisplayEmptyList() {
        val query = "Thinkpad T4893"
        val expected = listOf<Thinkpad>()
        coroutineRule.runBlockingTest {
            whenever(thinkpadRepo.getThinkpadsAlphaAscending(query))
                .thenReturn(flowOf(listOf()))
            whenever(prefDataStoreRepo.readSortOptionSetting)
                .thenReturn(flowOf(0))
            viewModel.getNewThinkpadListFromDatabase(query)
            val state = viewModel.uiState
            state.test {
                val actual = expectMostRecentItem() as ThinkpadListScreenState.ThinkpadListScreen
                assertTrue(actual.thinkpadList.isEmpty())
                assertEquals(expected, actual.thinkpadList)
            }
        }
    }

    @Test
    fun sortSelected_WhenNewSortOptionSelected_DisplayListFromNewSort() {
        val query = ""
        var notExpected = 3
        coroutineRule.runBlockingTest {
            var state = viewModel.uiState
            // Collect State Before New Sort option
            state.test {
                notExpected = (expectMostRecentItem() as
                        ThinkpadListScreenState.ThinkpadListScreen).sortOption
            }
            whenever(thinkpadRepo.getThinkpadsLowPriceFirst(query))
                .thenReturn(flowOf(expectedThinkpadDbList))
            // Pass New Sorting
            viewModel.sortSelected(3)
            verify(thinkpadRepo)
                .getThinkpadsLowPriceFirst(query)
            // Collect New State After New Sort option
            state = viewModel.uiState
            state.test {
                val actual = expectMostRecentItem() as ThinkpadListScreenState.ThinkpadListScreen
                assertNotEquals(notExpected, actual.sortOption)
            }
        }

    }
}