package work.racka.thinkrchive.ui.main.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import work.racka.thinkrchive.ui.components.CustomSearchBar
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme

@ExperimentalComposeUiApi
@Composable
fun ThinkpadListScreen(modifier: Modifier = Modifier) {

    val scrollState = rememberLazyListState()
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
            state = scrollState,
            modifier = modifier
        ) {
            item {
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
                Spacer(modifier = Modifier.height(Dimens.SmallPadding.size))
                Text(text = testText)
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
        ThinkpadListScreen()
    }
}
