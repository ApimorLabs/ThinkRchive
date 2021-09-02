package work.racka.thinkrchive.ui.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import timber.log.Timber
import work.racka.thinkrchive.ui.main.screenStates.ThinkpadDetailsScreenState
import work.racka.thinkrchive.ui.main.screenStates.ThinkpadListScreenState
import work.racka.thinkrchive.ui.main.screenStates.ThinkpadSettingsScreenState
import work.racka.thinkrchive.ui.main.screens.*
import work.racka.thinkrchive.ui.main.viewModel.ThinkpadDetailsViewModel
import work.racka.thinkrchive.ui.main.viewModel.ThinkpadListViewModel
import work.racka.thinkrchive.ui.main.viewModel.ThinkpadSettingsViewModel
import work.racka.thinkrchive.utils.scaleInEnterTransition
import work.racka.thinkrchive.utils.scaleInPopEnterTransition
import work.racka.thinkrchive.utils.scaleOutExitTransition
import work.racka.thinkrchive.utils.scaleOutPopExitTransition

@ExperimentalMaterialApi
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

        // Main List Screen
        composable(
            route = ThinkrchiveScreens.ThinkpadListScreen.name,

            // Transition animations
            enterTransition = { _, _ ->
                scaleInEnterTransition()
            },
            exitTransition = { _, _ ->
                scaleOutExitTransition()
            },
            // popEnter and popExit default to enterTransition & exitTransition respectively
            popEnterTransition = { _, _ ->
                scaleInPopEnterTransition()
            },
            popExitTransition = { _, _ ->
                scaleOutPopExitTransition()
            }
        ) {
            val viewModel: ThinkpadListViewModel = hiltViewModel()
            val thinkpadListState by viewModel.uiState.collectAsState()
            val thinkpadListScreenData =
                thinkpadListState as ThinkpadListScreenState.ThinkpadListScreen

            Timber.d("thinkpadListScreen NavHost called")

            ThinkpadListScreen(
                modifier = modifier,
                thinkpadList = thinkpadListScreenData.thinkpadList,
                networkLoading = thinkpadListScreenData.networkLoading,
                onSearch = { query ->
                    viewModel
                        .getNewThinkpadListFromDatabase(query)
                },
                onEntryClick = { thinkpad ->
                    navController.navigate(
                        route = "$thinkpadDetailsScreen/${thinkpad.model}"
                    )
                },
                networkError = thinkpadListScreenData.networkError,
                currentSortOption = thinkpadListScreenData.sortOption,
                onSortOptionClicked = { sort ->
                    viewModel.sortSelected(sort)
                },
                onSettingsClicked = {
                    navController.navigate(
                        route = ThinkrchiveScreens.ThinkpadSettingsScreen.name
                    )
                },
                onAboutClicked = {
                    navController.navigate(
                        route = ThinkrchiveScreens.ThinkpadAboutScreen.name
                    )
                },
                onCheckUpdates = {
                    // TODO: Check updates implementation
                }
            )
        }

        // Details Screen
        composable(
            route = "$thinkpadDetailsScreen/{thinkpad}",
            arguments = listOf(
                navArgument(name = "thinkpad") {
                    type = NavType.StringType
                }
            ),
            enterTransition = { _, _ ->
                scaleInEnterTransition()
            },
            exitTransition = { _, _ ->
                scaleOutExitTransition()
            },
            popEnterTransition = { _, _ ->
                scaleInPopEnterTransition()
            },
            popExitTransition = { _, _ ->
                scaleOutPopExitTransition()
            }
        ) {

            val thinkpadDetailsViewModel: ThinkpadDetailsViewModel = hiltViewModel()
            val thinkpadDetail = thinkpadDetailsViewModel.uiState.collectAsState()
            val context = LocalContext.current

            if (thinkpadDetail.value is ThinkpadDetailsScreenState.ThinkpadDetail) {
                val thinkpad =
                    (thinkpadDetail.value as ThinkpadDetailsScreenState.ThinkpadDetail).thinkpad
                val intent = remember {
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(thinkpad.psrefLink)
                    )
                }
                ThinkpadDetailsScreen(
                    modifier = modifier,
                    thinkpad = thinkpad,
                    onBackButtonPressed = {
                        navController.popBackStack()
                    },
                    onExternalLinkClicked = {
                        context.startActivity(intent)
                    }
                )
            }
        }


        // Settings Screen
        composable(
            route = ThinkrchiveScreens.ThinkpadSettingsScreen.name,
            enterTransition = { _, _ ->
                scaleInEnterTransition()
            },
            exitTransition = { _, _ ->
                scaleOutExitTransition()
            },
            popEnterTransition = { _, _ ->
                scaleInPopEnterTransition()
            },
            popExitTransition = { _, _ ->
                scaleOutPopExitTransition()
            }
        ) {
            val viewModel: ThinkpadSettingsViewModel = hiltViewModel()
            val settingsScreenState by viewModel.uiState.collectAsState()
            if (settingsScreenState is ThinkpadSettingsScreenState.ThinkpadSettings) {
                val settingsScreenData =
                    settingsScreenState as ThinkpadSettingsScreenState.ThinkpadSettings

                ThinkpadSettingsScreen(
                    currentTheme = settingsScreenData.themeOption,
                    currentSortOption = settingsScreenData.sortOption,
                    onThemeOptionClicked = {
                        viewModel.saveThemeSetting(it)
                    },
                    onSortOptionClicked = {
                        viewModel.saveSortOptionSetting(it)
                    },
                    onBackButtonPressed = {
                        navController.popBackStack()
                    }
                )
            }
        }

        // About Screen
        composable(
            route = ThinkrchiveScreens.ThinkpadAboutScreen.name,
            enterTransition = { _, _ ->
                scaleInEnterTransition()
            },
            exitTransition = { _, _ ->
                scaleOutExitTransition()
            },
            popEnterTransition = { _, _ ->
                scaleInPopEnterTransition()
            },
            popExitTransition = { _, _ ->
                scaleOutPopExitTransition()
            }
        ) {
            ThinkpadAboutScreen(
                onCheckUpdates = {
                    // TODO: Check updates implementation
                },
                onBackButtonPressed = {
                    navController.popBackStack()
                }
            )
        }
    }

}
