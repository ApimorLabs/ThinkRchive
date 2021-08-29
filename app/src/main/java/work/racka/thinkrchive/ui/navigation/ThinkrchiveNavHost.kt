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
import work.racka.thinkrchive.ui.main.screens.ThinkpadDetailsScreen
import work.racka.thinkrchive.ui.main.screens.ThinkpadListScreen
import work.racka.thinkrchive.ui.main.screens.ThinkrchiveScreens
import work.racka.thinkrchive.ui.main.states.ThinkpadDetailsScreenState
import work.racka.thinkrchive.ui.main.viewModel.ThinkpadDetailsViewModel
import work.racka.thinkrchive.ui.main.viewModel.ThinkpadListViewModel
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
                networkError = thinkpadListState.networkError,
                currentSortOption = thinkpadListState.sortOption,
                onSortOptionClicked = { sort ->
                    viewModel.sortSelected(sort)
                    viewModel.getNewThinkpadListFromDatabase()
                }
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
    }

}
