package work.racka.thinkrchive.ui.main.screens

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch
import timber.log.Timber
import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.ui.components.CustomSearchBar
import work.racka.thinkrchive.ui.components.ModalBottomSheet
import work.racka.thinkrchive.ui.components.ScrollToTopButton
import work.racka.thinkrchive.ui.components.ThinkpadEntry
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme
import work.racka.thinkrchive.utils.Constants

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ThinkpadListScreen(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    onEntryClick: (Thinkpad) -> Unit = { },
    onSearch: (String) -> Unit = { },
    onSortOptionClicked: (Int) -> Unit = { },
    onSettingsClicked: () -> Unit = { },
    onAboutClicked: () -> Unit = { },
    onCheckUpdates: () -> Unit = { },
    currentSortOption: Int,
    thinkpadList: List<Thinkpad>,
    networkLoading: Boolean,
    networkError: String
) {

    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ModalBottomSheetLayout(
            sheetContent = {
                ModalBottomSheet(
                    sheetState = sheetState,
                    scope = scope,
                    onSortOptionClicked = {
                        onSortOptionClicked(it)
                    },
                    currentSortOption = currentSortOption,
                    onSettingsClicked = onSettingsClicked,
                    onCheckUpdates = onCheckUpdates,
                    onAboutClicked = onAboutClicked
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
                Timber.d("thinkpadListScreen Contents called")
                item {
                    Spacer(modifier = Modifier.statusBarsPadding())
                    CustomSearchBar(
                        focusManager = focusManager,
                        onSearch = {
                            onSearch(it)
                        },
                        onDismissSearchClicked = {
                            onSearch("")
                        },
                        onOptionsClicked = {
                            scope.launch {
                                sheetState.show()
                            }
                        },
                        modifier = Modifier.padding(
                            vertical = Dimens.SmallPadding.size
                        )
                    )
                }
                items(thinkpadList) {
                    ThinkpadEntry(
                        thinkpad = it,
                        onEntryClick = { onEntryClick(it) },
                        modifier = Modifier
                            .padding(
                                horizontal = Dimens.MediumPadding.size,
                                vertical = Dimens.SmallPadding.size
                            )
                    )
                }
                item {
                    if (networkLoading) {
                        CircularProgressIndicator()
                    }
                }
                item {
                    if (networkError.isNotBlank()) {
                        Text(
                            text = networkError,
                            color = MaterialTheme.colors.error,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // This must always stay at the bottom for navBar padding
                item {
                    Spacer(modifier = Modifier.navigationBarsPadding())
                }
            }
            ScrollToTopButton(
                listState = listState,
                modifier = Modifier.fillMaxWidth(),
                scope = scope
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview(
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    device = Devices.PIXEL_4
)
@Composable
private fun ThinkpadListScreenPreview() {
    ThinkRchiveTheme {
        ThinkpadListScreen(
            thinkpadList = Constants.ThinkpadsListPreview,
            networkLoading = false,
            networkError = "",
            currentSortOption = 0
        )
    }
}
