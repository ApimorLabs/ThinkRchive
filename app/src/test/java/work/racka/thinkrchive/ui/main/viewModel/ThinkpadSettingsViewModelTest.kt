package work.racka.thinkrchive.ui.main.viewModel

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.clearInvocations
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import work.racka.thinkrchive.data.local.dataStore.PrefDataStore
import work.racka.thinkrchive.testUtils.MainCoroutineRule
import work.racka.thinkrchive.ui.main.screenStates.ThinkpadSettingsScreenState
import work.racka.thinkrchive.ui.theme.Theme
import work.racka.thinkrchive.utils.Sort
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class ThinkpadSettingsViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var dataStore: PrefDataStore
    private lateinit var viewModel: ThinkpadSettingsViewModel

    private val expectTheme = Theme.FOLLOW_SYSTEM
    private val expectedSort = Sort.ALPHABETICAL_ASC

    @Before
    fun setUp() {
        dataStore = mock()
        whenever(dataStore.readThemeSetting)
            .thenReturn(flowOf(expectTheme.themeValue))
        whenever(dataStore.readSortOptionSetting)
            .thenReturn(flowOf(expectedSort.sortValue))
        viewModel = ThinkpadSettingsViewModel(dataStore)
    }

    @After
    fun tearDown() {
        clearInvocations(dataStore)
    }

    @Test
    fun uiState_GetLatestThinkpadSettingsScreenState() {
        val expected = ThinkpadSettingsScreenState.ThinkpadSettings(
            themeOption = expectTheme.themeValue,
            sortOption = expectedSort.sortValue
        )
        coroutineRule.runBlocking {
            whenever(dataStore.readSortOptionSetting)
                .thenReturn(flowOf(expectedSort.sortValue))
            whenever(dataStore.readThemeSetting)
                .thenReturn(flowOf(expectTheme.themeValue))
            verify(dataStore).apply {
                readSortOptionSetting
                readThemeSetting
            }
            val state = viewModel.uiState
            state.test {
                val actual = expectMostRecentItem()
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun saveThemeSetting_WhenThemeValueProvided_PrefDataStoreSavesValue() {
        val themeValue = Theme.LIGHT_THEME.themeValue
        coroutineRule.runBlocking {
            whenever(dataStore.saveThemeSetting(themeValue))
                .thenReturn(Unit)
            viewModel.saveThemeSetting(themeValue)
            verify(dataStore)
                .saveThemeSetting(themeValue)
        }
    }

    @Test
    fun saveSortOptionSetting_WhenSortValueProvided_PrefDataStoreSavesSortValue() {
        val sortValue = Sort.HIGH_PRICE_FIRST.sortValue
        coroutineRule.runBlocking {
            whenever(dataStore.saveSortOptionSetting(sortValue))
                .thenReturn(Unit)
            viewModel.saveSortOptionSetting(sortValue)
            verify(dataStore)
                .saveSortOptionSetting(sortValue)
        }
    }
}