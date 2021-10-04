package work.racka.thinkrchive.ui.main.viewModel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.clearInvocations
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import work.racka.thinkrchive.data.dataTransferObjects.asDatabaseModel
import work.racka.thinkrchive.data.dataTransferObjects.asThinkpad
import work.racka.thinkrchive.repository.ThinkpadRepository
import work.racka.thinkrchive.testUtils.FakeThinkpadData
import work.racka.thinkrchive.testUtils.MainCoroutineRule
import work.racka.thinkrchive.ui.main.screenStates.ThinkpadDetailsScreenState
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class ThinkpadDetailsViewModelTest {


    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var thinkpadRepo: ThinkpadRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: ThinkpadDetailsViewModel

    private val expectedThinkpad = FakeThinkpadData.fakeResponseList
        .asDatabaseModel()
        .toList()
        .first()

    @Before
    fun setUp() {
        thinkpadRepo = mock()
        whenever(thinkpadRepo.getThinkpad(expectedThinkpad.model))
            .thenReturn(flowOf(expectedThinkpad))
        savedStateHandle = mock()
        whenever(savedStateHandle.get<String>("thinkpad"))
            .thenReturn(expectedThinkpad.model)
        viewModel = ThinkpadDetailsViewModel(thinkpadRepo, savedStateHandle)
    }

    @After
    fun tearDown() {
        clearInvocations(thinkpadRepo, savedStateHandle)
    }

    @Test
    fun uIState_GetsLatestDetailsScreenUiState() {
        val expected = ThinkpadDetailsScreenState.ThinkpadDetail(
            expectedThinkpad.asThinkpad()
        )
        coroutineRule.runBlockingTest {
            whenever(thinkpadRepo.getThinkpad(expectedThinkpad.model))
                .thenReturn(flowOf(expectedThinkpad))
            val state = viewModel.uiState
            state.test {
                val actual = expectMostRecentItem()
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun getThinkpad_WhenThinkpadFoundInDb_PostsThinkpadToUiState() {
        val expected = expectedThinkpad.asThinkpad()
        coroutineRule.runBlockingTest {
            whenever(savedStateHandle.get<String>("thinkpad"))
                .thenReturn(expectedThinkpad.model)
            whenever(thinkpadRepo.getThinkpad(expectedThinkpad.model))
                .thenReturn(flowOf(expectedThinkpad))
            val state = viewModel.uiState
            state.test {
                val actual = expectMostRecentItem() as ThinkpadDetailsScreenState.ThinkpadDetail
                assertEquals(expected, actual.thinkpad)
            }
        }
    }

    @Test(expected = NullPointerException::class)
    fun getThinkpad_WhenThinkpadNameIsNull_ThrowsNullPointerException_And_UiStateIsEmpty() {
        val expectedState = ThinkpadDetailsScreenState.EmptyState
        val nullString: String? = null
        coroutineRule.runBlockingTest {
            whenever(savedStateHandle.get<String>("thinkpad"))
                .thenReturn(nullString)
            whenever(thinkpadRepo.getThinkpad(nullString!!))
                .thenThrow(NullPointerException::class.java)
            val state = viewModel.uiState
            state.test {
                val actual = expectMostRecentItem()
                assertEquals(expectedState, actual)
            }
        }
    }
}