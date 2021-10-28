package work.racka.thinkrchive.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import timber.log.Timber
import work.racka.thinkrchive.ui.main.screenStates.ThinkpadListScreenState
import work.racka.thinkrchive.ui.main.screens.ThinkpadListScreen
import work.racka.thinkrchive.ui.main.screens.ThinkrchiveScreens
import work.racka.thinkrchive.ui.main.viewModel.ThinkpadListViewModel
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme


@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ThinkrchiveApp(themeValue: Int = 0) {
    ProvideWindowInsets {
        ThinkRchiveTheme(theme = themeValue) {
            Timber.d("ThinkrchiveApp called")
            val navController = rememberAnimatedNavController()
            ThinkrchiveNavHost(
                modifier = Modifier,
                navController = navController
            )

//            val viewModel: ThinkpadListViewModel = hiltViewModel()
//            val thinkpadListState by viewModel.uiState.collectAsState()
//            val thinkpadListScreenData =
//                thinkpadListState as ThinkpadListScreenState.ThinkpadListScreen
//
//            Timber.d("thinkpadListScreen NavHost called")
//
//            ThinkpadListScreen(
//                modifier = Modifier,
//                thinkpadList = thinkpadListScreenData.thinkpadList,
//                networkLoading = thinkpadListScreenData.networkLoading,
//                onSearch = { query ->
//                    viewModel
//                        .getNewThinkpadListFromDatabase(query)
//                },
//                onEntryClick = { thinkpad ->
//
//                },
//                networkError = thinkpadListScreenData.networkError,
//                currentSortOption = thinkpadListScreenData.sortOption,
//                onSortOptionClicked = { sort ->
//                    viewModel.sortSelected(sort)
//                },
//                onSettingsClicked = {
//
//                },
//                onAboutClicked = {
//
//                },
//                onDonateClicked = {
//
//                }
//            )
        }
    }
}