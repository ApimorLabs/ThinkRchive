package work.racka.thinkrchive.ui.main.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import work.racka.thinkrchive.ui.components.SettingsEntry
import work.racka.thinkrchive.ui.components.SettingsEntryBottomSheet
import work.racka.thinkrchive.ui.components.TopCollapsingToolbar
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.Theme
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme

@ExperimentalMaterialApi
@Composable
fun ThinkpadSettingsScreen(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    onThemeOptionClicked: (Int) -> Unit = { },
    onBackButtonPressed: () -> Unit = { },
    currentTheme: Int
) {

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

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
        ModalBottomSheetLayout(
            sheetContent = {
                SettingsEntryBottomSheet(
                    sheetState = sheetState,
                    scope = scope,
                    currentTheme = currentTheme,
                    onThemeOptionClicked = {
                        onThemeOptionClicked(it)
                    }
                )
            },
            sheetState = sheetState,
            sheetElevation = 0.dp,
            sheetBackgroundColor = Color.Transparent
        ) {
            LazyColumn(
                state = listState,
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    val theme = when(currentTheme) {
                        1 -> Theme.LIGHT_THEME
                        2 -> Theme.DARK_THEME
                        else -> Theme.FOLLOW_SYSTEM
                    }
                    SettingsEntry(
                        modifier = Modifier.padding(Dimens.MediumPadding.size),
                        settingsEntryName = "Theme Options",
                        currentSettingValue = theme.themeName,
                        currentSettingIcon = theme.icon,
                        onSettingsEntryClick = {
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
        ThinkpadSettingsScreen(currentTheme = 2)
    }
}