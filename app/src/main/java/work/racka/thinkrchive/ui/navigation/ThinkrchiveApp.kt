package work.racka.thinkrchive.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme

@ExperimentalComposeUiApi
@Composable
fun ThinkrchiveApp() {
    ProvideWindowInsets {
        ThinkRchiveTheme {
            ThinkrchiveNavHost(
                modifier = Modifier.statusBarsPadding()
            )
        }
    }
}