package work.racka.thinkrchive.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import timber.log.Timber
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme

@ExperimentalComposeUiApi
@Composable
fun ThinkrchiveApp() {
    ProvideWindowInsets {
        ThinkRchiveTheme {
            Timber.d("ThinkrchiveApp called")
            val navController = rememberNavController()
            ThinkrchiveNavHost(
                modifier = Modifier,
                navController = navController
            )
        }
    }
}