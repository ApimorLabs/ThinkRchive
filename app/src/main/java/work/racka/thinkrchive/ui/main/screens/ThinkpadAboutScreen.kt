package work.racka.thinkrchive.ui.main.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import work.racka.thinkrchive.BuildConfig
import work.racka.thinkrchive.R
import work.racka.thinkrchive.ui.components.AboutTopSection
import work.racka.thinkrchive.ui.components.ThinkpadEntry
import work.racka.thinkrchive.ui.components.TopCollapsingToolbar
import work.racka.thinkrchive.utils.Constants

@Composable
fun ThinkpadAboutScreen(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    onCheckUpdates: () -> Unit = { },
    onBackButtonPressed: () -> Unit = { }
) {


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopCollapsingToolbar(
                toolbarHeading = "About",
                listState = listState,
                onBackButtonPressed = onBackButtonPressed
            ) {
                AboutTopSection(
                    appName = stringResource(id = R.string.app_name),
                    version = BuildConfig.VERSION_NAME,
                    appLogo = painterResource(id = R.drawable.app_icon),
                    onCheckUpdatesClicked = onCheckUpdates
                )
            }
        }
    ) {
        LazyColumn(
            modifier = modifier,
            state = listState
        ) {
            items(100) {
                Text(text = it.toString(), modifier = Modifier.fillMaxWidth())
            }
        }

    }
}