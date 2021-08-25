package work.racka.thinkrchive.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
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

    AnimatedNavHost(
        navController = navController,
        startDestination = ThinkrchiveScreens.ThinkpadListScreen.name
    ) {
        Timber.d("thinkpadNavHost called")
        val thinkpadDetailsScreen = ThinkrchiveScreens.ThinkpadDetailsScreen.name

        composable(
            route = ThinkrchiveScreens.ThinkpadListScreen.name,

            // Transition animations
            enterTransition = { _, _ ->
                scaleIn(
                    initialScale = .9f,
                    animationSpec = tween(300)
                ) + fadeIn(
                    animationSpec = tween(300)
                )
            },
            exitTransition = { _, _ ->
                scaleOut(
                    targetScale = 1.1f,
                    animationSpec = tween(300)
                ) + fadeOut(
                    animationSpec = tween(300)
                )
            },
            // popEnter and popExit default to enterTransition & exitTransition respectively
            popEnterTransition = { _, _ ->
                scaleIn(
                    initialScale = 1.1f,
                    animationSpec = tween(300)
                ) + fadeIn(
                    animationSpec = tween(300)
                )
            },
            popExitTransition = { _, _ ->
                scaleOut(
                    targetScale = .9f,
                    animationSpec = tween(300)
                ) + fadeOut(
                    animationSpec = tween(300)
                )
            }
        ) {
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
            ),

            // Transition animations
            enterTransition = { _, _ ->
                scaleIn(
                    initialScale = .9f,
                    animationSpec = tween(300)
                ) + fadeIn(
                    animationSpec = tween(300)
                )
            },
            exitTransition = { _, _ ->
                scaleOut(
                    targetScale = 1.1f,
                    animationSpec = tween(300)
                ) + fadeOut(
                    animationSpec = tween(300)
                )
            },
            // popEnter and popExit default to enterTransition & exitTransition respectively
            popEnterTransition = { _, _ ->
                scaleIn(
                    initialScale = 1.1f,
                    animationSpec = tween(300)
                ) + fadeIn(
                    animationSpec = tween(300)
                )
            },
            popExitTransition = { _, _ ->
                scaleOut(
                    targetScale = .9f,
                    animationSpec = tween(300)
                ) + fadeOut(
                    animationSpec = tween(300)
                )
            }

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