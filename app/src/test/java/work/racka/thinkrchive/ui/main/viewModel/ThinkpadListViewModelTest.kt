package work.racka.thinkrchive.ui.main.viewModel

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.clearInvocations
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import work.racka.thinkrchive.data.dataTransferObjects.asDatabaseModel
import work.racka.thinkrchive.data.dataTransferObjects.asDomainModel
import work.racka.thinkrchive.data.database.ThinkpadDatabaseObject
import work.racka.thinkrchive.domain.model.Thinkpad
import work.racka.thinkrchive.repository.DataStoreRepository
import work.racka.thinkrchive.repository.ThinkpadRepository
import work.racka.thinkrchive.testUtils.MainCoroutineRule
import work.racka.thinkrchive.testUtils.FakeThinkpadLists
import work.racka.thinkrchive.ui.main.screenStates.ThinkpadListScreenState
import work.racka.thinkrchive.utils.Resource
import kotlin.time.ExperimentalTime

class ThinkpadListViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var thinkpadRepo: ThinkpadRepository
    private lateinit var dataStoreRepo: DataStoreRepository
    private lateinit var viewModel: ThinkpadListViewModel

    private val expectedThinkpadList = FakeThinkpadLists.fakeResponseList
        .asDatabaseModel()
        .toList()
        .asDomainModel()
    private val expectedThinkpadDbList = FakeThinkpadLists.fakeResponseList
        .asDatabaseModel()
        .toList()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        thinkpadRepo = mock()
        dataStoreRepo = mock()
        coroutineRule.runBlockingTest {
            val insertFlow =
                flow { emit(expectedThinkpadDbList) }
            whenever(dataStoreRepo.readSortOptionSetting)
                .thenReturn(flow { emit(0) })
            whenever(thinkpadRepo.getThinkpadsAlphaAscending(""))
                .thenReturn(insertFlow)
            viewModel = ThinkpadListViewModel(thinkpadRepo, dataStoreRepo)
        }
    }

    @After
    fun tearDown() {
        clearInvocations(thinkpadRepo, dataStoreRepo)
    }

    @ExperimentalTime
    @ExperimentalCoroutinesApi
    @Test
    fun canGetUiStateTest() {
        val query = ""
        val expected = ThinkpadListScreenState.ThinkpadListScreen()
        coroutineRule.runBlockingTest {
            whenever(thinkpadRepo.getAllThinkpadsFromNetwork())
                .thenReturn(Resource.Success(data = listOf()))
            whenever(dataStoreRepo.readSortOptionSetting)
                .thenReturn(flow { emit(0) })
            whenever(thinkpadRepo.getThinkpadsAlphaAscending(query))
                .thenReturn(flow { emit(listOf<ThinkpadDatabaseObject>()) })
            viewModel.refreshThinkpadList()
            viewModel.getNewThinkpadListFromDatabase(query)
            val state = viewModel.uiState
            state.test {
                val actual = expectMostRecentItem()
                assertEquals(expected, actual)
            }
        }
    }

    @ExperimentalTime
    @ExperimentalCoroutinesApi
    @Test
    fun refreshThinkpadListTest() {
        val query = ""
        val notExpected = expectedThinkpadList
        coroutineRule.runBlockingTest {
            val refreshedList = expectedThinkpadDbList.subList(0, 2)
            whenever(thinkpadRepo.getAllThinkpadsFromNetwork())
                .thenReturn(
                    Resource.Success(
                        data = FakeThinkpadLists.fakeResponseList
                            .subList(0, 2)
                    )
                )
            whenever(thinkpadRepo.getThinkpadsAlphaAscending(query))
                .thenReturn(flow { emit(refreshedList) })

            viewModel.getNewThinkpadListFromDatabase(query)
            viewModel.refreshThinkpadList()
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

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun getNewThinkpadListFromDatabaseTest() {
        val query = ""
        val expected = expectedThinkpadList
        coroutineRule.runBlockingTest {
            val insertFlow =
                flow { emit(expectedThinkpadDbList) }
            whenever(thinkpadRepo.getThinkpadsAlphaAscending(query))
                .thenReturn(insertFlow)
            whenever(dataStoreRepo.readSortOptionSetting)
                .thenReturn(
                    flow { emit(0) }
                )
            viewModel.getNewThinkpadListFromDatabase(query)
            val state = viewModel.uiState
            state.test {
                val actual = expectMostRecentItem() as ThinkpadListScreenState.ThinkpadListScreen
                assertEquals(expected, actual.thinkpadList)
            }
        }

    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun getNewThinkpadListFromDatabaseWithSearchQueryFoundTest() {
        val query = "Thinkpad T450"
        val expected = expectedThinkpadList.subList(1, expectedThinkpadList.lastIndex)
        coroutineRule.runBlockingTest {
            val firstItem = expectedThinkpadDbList.subList(1, expectedThinkpadDbList.lastIndex)
            whenever(thinkpadRepo.getThinkpadsAlphaAscending(query))
                .thenReturn(
                    flow { emit(firstItem) }
                )
            whenever(dataStoreRepo.readSortOptionSetting)
                .thenReturn(
                    flow { emit(0) }
                )
            viewModel.getNewThinkpadListFromDatabase(query)
            val state = viewModel.uiState
            state.test {
                val actual = expectMostRecentItem() as ThinkpadListScreenState.ThinkpadListScreen
                assertTrue(actual.thinkpadList.isNotEmpty())
                assertEquals(expected, actual.thinkpadList)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun getNewThinkpadListFromDatabaseWithSearchQueryNotFoundTest() {
        val query = "Thinkpad T4893"
        val expected = listOf<Thinkpad>()
        coroutineRule.runBlockingTest {
            whenever(thinkpadRepo.getThinkpadsAlphaAscending(query))
                .thenReturn(
                    flow { emit(listOf<ThinkpadDatabaseObject>()) }
                )
            whenever(dataStoreRepo.readSortOptionSetting)
                .thenReturn(
                    flow { emit(0) }
                )
            viewModel.getNewThinkpadListFromDatabase(query)
            val state = viewModel.uiState
            state.test {
                val actual = expectMostRecentItem() as ThinkpadListScreenState.ThinkpadListScreen
                assertTrue(actual.thinkpadList.isEmpty())
                assertEquals(expected, actual.thinkpadList)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun sortSelectedTest() {
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
                .thenReturn(flow { emit(expectedThinkpadDbList) })
            // Pass New Sorting
            viewModel.sortSelected(3)
            // Collect New State After New Sort option
            state = viewModel.uiState
            state.test {
                val actual = expectMostRecentItem() as ThinkpadListScreenState.ThinkpadListScreen
                assertNotEquals(notExpected, actual.sortOption)
            }
        }

    }
}