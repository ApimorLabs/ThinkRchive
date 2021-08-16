package work.racka.thinkrchive.ui.main.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import timber.log.Timber
import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.ui.components.CustomSearchBar
import work.racka.thinkrchive.ui.components.ThinkpadEntry
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme
import work.racka.thinkrchive.utils.Constants

@ExperimentalComposeUiApi
@Composable
fun ThinkpadListScreen(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    onEntryClick: () -> Unit = { },
    thinkpadList: List<Thinkpad>
) {

    val focusManager = LocalFocusManager.current

    var testText by remember {
        mutableStateOf("Initial")
    }
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            state = listState,
            modifier = modifier
        ) {
            Timber.d("thinkpadListScreen Contents called")
            item {
                Spacer(modifier = Modifier.statusBarsPadding())
                CustomSearchBar(
                    focusManager = focusManager,
                    onSearch = {
                        testText = it
                    },
                    modifier = Modifier.padding(
                        vertical = Dimens.SmallPadding.size,
                        horizontal = Dimens.MediumPadding.size
                    )
                )
            }
            items(thinkpadList) {
                ThinkpadEntry(
                    thinkpad = it,
                    onEntryClick = onEntryClick,
                    modifier = Modifier
                        .padding(
                            horizontal = Dimens.MediumPadding.size,
                            vertical = Dimens.SmallPadding.size
                        )
                )
            }

            // This must always stay at the bottom for navBar padding
            item {
                Spacer(modifier = Modifier.navigationBarsPadding())
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview(
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    device = Devices.PIXEL_4
)
@Composable
fun ThinkpadListScreenPreview() {
    ThinkRchiveTheme {
        ThinkpadListScreen(
            thinkpadList = Constants.ThinkpadsListPreview
        )
    }
}
