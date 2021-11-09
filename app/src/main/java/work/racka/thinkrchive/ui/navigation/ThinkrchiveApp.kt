package work.racka.thinkrchive.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import timber.log.Timber
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
        }
    }
}