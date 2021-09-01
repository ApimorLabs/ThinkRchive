package work.racka.thinkrchive.ui.main.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import work.racka.thinkrchive.ui.components.*
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.Theme
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme
import work.racka.thinkrchive.utils.Constants
import work.racka.thinkrchive.utils.Sort

@ExperimentalMaterialApi
@Composable
fun ThinkpadSettingsScreen(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    onThemeOptionClicked: (Int) -> Unit = { },
    onSortOptionClicked: (Int) -> Unit = { },
    onBackButtonPressed: () -> Unit = { },
    currentTheme: Int,
    currentSortOption: Int
) {

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val settingsEntryName = rememberSaveable {
        mutableStateOf("")
    }

    ModalBottomSheetLayout(
        sheetContent = {
            SettingEntrySheet(
                sheetState = sheetState,
                scope = scope,
                settingsEntryName = settingsEntryName.value,
                currentSortOption = currentSortOption,
                currentTheme = currentTheme,
                onSortOptionClicked = {
                    onSortOptionClicked(it)
                },
                onThemeOptionClicked = {
                    onThemeOptionClicked(it)
                }
            )

        },
        sheetState = sheetState,
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopCollapsingToolbar(
                    toolbarHeading = "Settings",
                    listState = listState,
                    onBackButtonPressed = onBackButtonPressed
                )
            }
        ) {
            LazyColumn(
                state = listState,
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    val theme = when(currentTheme) {
                        1 -> Theme.DARK_THEME
                        2 -> Theme.LIGHT_THEME
                        else -> Theme.FOLLOW_SYSTEM
                    }
                    SettingsEntry(
                        modifier = Modifier.padding(
                            vertical = Dimens.SmallPadding.size,
                            horizontal = Dimens.MediumPadding.size
                        ),
                        settingsEntryName = Constants.THEME_OPTIONS,
                        currentSettingValue = theme.themeName,
                        currentSettingIcon = theme.icon,
                        onSettingsEntryClick = {
                            settingsEntryName.value = it
                            scope.launch {
                                sheetState.show()
                            }
                        }
                    )
                }
                item {
                    val sort = when(currentSortOption) {
                        1 -> Sort.NEW_RELEASE_FIRST
                        2 -> Sort.OLD_RELEASE_FIRST
                        3 -> Sort.LOW_PRICE_FIRST
                        4 -> Sort.HIGH_PRICE_FIRST
                        else -> Sort.ALPHABETICAL_ASC
                    }
                    SettingsEntry(
                        modifier = Modifier.padding(
                            vertical = Dimens.SmallPadding.size,
                            horizontal = Dimens.MediumPadding.size
                        ),
                        settingsEntryName = Constants.SORT_OPTIONS,
                        currentSettingValue = sort.type,
                        currentSettingIcon = sort.icon,
                        onSettingsEntryClick = {
                            settingsEntryName.value = it
                            scope.launch {
                                sheetState.show()
                            }
                        }
                    )
                }
            }
        }

    }
}

@ExperimentalMaterialApi
@Preview(
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    device = Devices.PIXEL_4
)
@Composable
private fun ThinkpadSettingsScreenPrev() {
    ThinkRchiveTheme {
        ThinkpadSettingsScreen(currentTheme = 2, currentSortOption = 1)
    }
}