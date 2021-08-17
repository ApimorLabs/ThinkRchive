package work.racka.thinkrchive.ui.navigation

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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import work.racka.thinkrchive.data.dataTransferObjects.asThinkpad
import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.ui.main.screens.ThinkpadListScreen
import work.racka.thinkrchive.ui.main.screens.ThinkrchiveScreens
import work.racka.thinkrchive.ui.main.viewModel.ThinkpadDetailsViewModel
import work.racka.thinkrchive.ui.main.viewModel.ThinkpadListViewModel
import work.racka.thinkrchive.utils.emptyThinkpad

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
            val thinkpadListViewModel: ThinkpadListViewModel = hiltViewModel()
            val thinkpadList by remember {
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
                thinkpadList = thinkpadList,
                networkLoading = isLoading,
                onSearch = { query ->
                    thinkpadListViewModel
                        .getNewThinkpadListFromDatabase(query)
                },
                onEntryClick = { thinkpad ->
                    navController.navigate(
                        route = "$thinkpadDetailsScreen/${thinkpad.model}"
                    )
                }
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
            val scope = rememberCoroutineScope()
            val thinkpadDetailsViewModel: ThinkpadDetailsViewModel = hiltViewModel()
            val thinkpad = remember {
                var temp: Thinkpad = emptyThinkpad()
                scope.launch {
                    thinkpadDetailsViewModel.thinkpad.collect {
                        temp = it.asThinkpad()
                    }
                }
                temp
            }

            Text(
                text = thinkpad.model,
                modifier = Modifier.statusBarsPadding()
            )
        }
    }

}