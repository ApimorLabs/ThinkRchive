package work.racka.thinkrchive.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.google.accompanist.insets.statusBarsPadding
import timber.log.Timber
import work.racka.thinkrchive.ui.main.screens.ThinkpadDetailsScreen
import work.racka.thinkrchive.ui.main.screens.ThinkpadListScreen
import work.racka.thinkrchive.ui.main.screens.ThinkrchiveScreens
import work.racka.thinkrchive.ui.main.states.ThinkpadDetailsScreenState
import work.racka.thinkrchive.ui.main.viewModel.ThinkpadDetailsViewModel
import work.racka.thinkrchive.ui.main.viewModel.ThinkpadListViewModel

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ThinkrchiveNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = ThinkrchiveScreens.ThinkpadListScreen.name
    ) {
        Timber.d("thinkpadNavHost called")
        val thinkpadDetailsScreen = ThinkrchiveScreens.ThinkpadDetailsScreen.name

        composable(route = ThinkrchiveScreens.ThinkpadListScreen.name) {
            val viewModel: ThinkpadListViewModel = hiltViewModel()
            val thinkpadListState by viewModel.uiState.collectAsState()

            Timber.d("thinkpadListScreen NavHost called")

            ThinkpadListScreen(
                modifier = modifier,
                thinkpadList = thinkpadListState.thinkpadList,
                networkLoading = thinkpadListState.networkLoading,
                onSearch = { query ->
                    viewModel
                        .getNewThinkpadListFromDatabase(query)
                },
                onEntryClick = { thinkpad ->
                    navController.navigate(
                        route = "$thinkpadDetailsScreen/${thinkpad.model}"
                    )
                },
                networkError = thinkpadListState.networkError
            )
        }

        composable(
            route = "$thinkpadDetailsScreen/{thinkpad}",
            arguments = listOf(
                navArgument(name = "thinkpad") {
                    type = NavType.StringType
                }
            )
        ) {

            val thinkpadDetailsViewModel: ThinkpadDetailsViewModel = hiltViewModel()
            val thinkpadDetail = thinkpadDetailsViewModel.uiState.collectAsState()

            if (thinkpadDetail.value is ThinkpadDetailsScreenState.ThinkpadDetail) {
                val thinkpad =
                    (thinkpadDetail.value as ThinkpadDetailsScreenState.ThinkpadDetail).thinkpad
                ThinkpadDetailsScreen(
                    modifier = modifier,
                    thinkpad = thinkpad,
                    onBackButtonPressed = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }

}