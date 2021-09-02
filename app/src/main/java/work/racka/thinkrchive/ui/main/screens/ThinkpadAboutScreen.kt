package work.racka.thinkrchive.ui.main.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsPadding
import work.racka.thinkrchive.BuildConfig
import work.racka.thinkrchive.R
import work.racka.thinkrchive.ui.components.AboutTopSection
import work.racka.thinkrchive.ui.components.CollapsingToolbarBase
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme

@Composable
fun ThinkpadAboutScreen(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    onCheckUpdates: () -> Unit = { },
    onBackButtonPressed: () -> Unit = { }
) {
    // CollapsingToolbar Implementation
    val toolbarHeight = 300.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                // Returning Zero so we just observe the scroll but don't execute it
                return Offset.Zero
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CollapsingToolbarBase(
                toolbarHeading = "About",
                toolbarHeight = toolbarHeight,
                toolbarOffset = toolbarOffsetHeightPx.value,
                onBackButtonPressed = onBackButtonPressed
            ) {
                AboutTopSection(
                    appName = stringResource(id = R.string.app_name),
                    version = BuildConfig.VERSION_NAME,
                    appLogo = painterResource(id = R.drawable.app_icon),
                    onCheckUpdatesClicked = onCheckUpdates
                )
            }
        },
        bottomBar = {
            Text(
                text = stringResource(id = R.string.made_with_text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.SmallPadding.size)
                    .navigationBarsPadding(),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onSurface
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    ) {
        LazyColumn(
            modifier = modifier
                .nestedScroll(nestedScrollConnection),
            state = listState
        ) {

        }

    }
}

@Preview
@Composable
private fun AboutScreenPrev() {
    ThinkRchiveTheme {
        ThinkpadAboutScreen()
    }
}
