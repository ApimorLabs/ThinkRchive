package work.racka.thinkrchive.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import timber.log.Timber
import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.ui.main.screens.ThinkpadListScreen
import work.racka.thinkrchive.ui.main.screens.ThinkrchiveScreens
import work.racka.thinkrchive.ui.main.viewModel.ThinkpadListViewModel

@ExperimentalComposeUiApi
@Composable
fun ThinkrchiveNavHost(
    modifier: Modifier = Modifier,
    thinkpadListViewModel: ThinkpadListViewModel,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = ThinkrchiveScreens.ThinkpadListScreen.name
    ) {
        Timber.d("thinkpadNavHost called")
        val thinkpadDetailsScreen = ThinkrchiveScreens.ThinkpadDetailsScreen.name

        composable(route = ThinkrchiveScreens.ThinkpadListScreen.name) {
            val thinkpadList by  remember {
                derivedStateOf {
                    thinkpadListViewModel.thinkpadList
                }
            }
            val loadError by remember {
                thinkpadListViewModel.loadError
            }
            val isLoading by remember {
                thinkpadListViewModel.isLoading
            }
            Timber.d("thinkpadListScreen NavHost called")

            ThinkpadListScreen(
                modifier = modifier,
                thinkpadList = thinkpadList
            )
        }

        composable(
            route = "$thinkpadDetailsScreen/{thinkpad}",
            arguments = listOf(
                navArgument(name = "thinkpad") {
                    type = NavType.ParcelableType(Thinkpad::class.java)
                }
            )
        ) { entry ->

        }
    }

}