package work.racka.thinkrchive.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.ui.main.screens.ThinkpadListScreen
import work.racka.thinkrchive.ui.main.viewModel.ThinkpadListViewModel

@ExperimentalComposeUiApi
@Composable
fun ThinkrchiveNavHost(
    modifier: Modifier = Modifier,
    thinkpadListViewModel: ThinkpadListViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "thinkpadListScreen"
    ) {

        composable(route = "thinkpadListScreen") {
            ThinkpadListScreen(modifier = modifier)
        }

        composable(
            route = "thinkpadDetailsScreen/{thinkpad}",
            arguments = listOf(
                navArgument(name = "thinkpad") {
                    type = NavType.ParcelableType(Thinkpad::class.java)
                }
            )
        ) { entry ->

        }
    }

}