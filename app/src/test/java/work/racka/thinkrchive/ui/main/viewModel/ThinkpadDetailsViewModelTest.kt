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
import org.mockito.kotlin.doReturn
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
    fun setUp() = coroutineRule.runBlockingTest {
        thinkpadRepo = mock {
            on { getThinkpad(expectedThinkpad.model) }
            doReturn(flowOf(expectedThinkpad))
        }
        savedStateHandle = SavedStateHandle(mapOf(Pair("thinkpad", "thinkpad")))
//        whenever(savedStateHandle.get<String>(""))
//            .thenReturn(expectedThinkpad.model)
        println("Handle is null: ${savedStateHandle.equals(null)}")
        println("Repo is null: ${thinkpadRepo.equals(null)}")
        viewModel = ThinkpadDetailsViewModel(thinkpadRepo, savedStateHandle)
    }

    @After
    fun tearDown() {
        clearInvocations(thinkpadRepo, savedStateHandle)
    }

    @Test
    fun uIState_GetsLatestDetailsScreenUiState() {
        val expected = expectedThinkpad.asThinkpad()
        coroutineRule.runBlockingTest {
            val state = viewModel.uiState
            state.test {
                val actual = expectMostRecentItem() as ThinkpadDetailsScreenState.ThinkpadDetail
                assertEquals(expected, actual.thinkpad)
            }
        }
    }
}