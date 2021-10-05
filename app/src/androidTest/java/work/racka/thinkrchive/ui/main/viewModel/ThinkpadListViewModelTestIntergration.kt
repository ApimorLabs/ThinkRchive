package work.racka.thinkrchive.ui.main.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import work.racka.thinkrchive.ui.main.MainActivity
import work.racka.thinkrchive.data.dataTransferObjects.asDatabaseModel
import work.racka.thinkrchive.data.dataTransferObjects.asDomainModel
import work.racka.thinkrchive.testUtils.TestData
import work.racka.thinkrchive.ui.main.screenStates.ThinkpadListScreenState
import kotlin.time.ExperimentalTime

// Always Fails. Need to find a better way to inject viewModel here
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ThinkpadListViewModelTestIntergration {
    @get:Rule(order = 0)
    val hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 2)
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ThinkpadListViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.setContent {
            viewModel = hiltViewModel()
        }
    }

    @ExperimentalTime
    @ExperimentalCoroutinesApi
    @Test
    fun checkDisplayedThinkpadList() = runBlockingTest {
        viewModel.refreshThinkpadList()
        val expected = TestData.thinkpadResponseList
            .asDatabaseModel()
            .toList()
            .asDomainModel()
        val screenState = viewModel.uiState
        screenState.test {
            val actual = expectMostRecentItem() as ThinkpadListScreenState.ThinkpadListScreen
            //assertTrue(actual.thinkpadList.isNotEmpty())
            //assertEquals(expected, actual.thinkpadList)
        }
    }
}