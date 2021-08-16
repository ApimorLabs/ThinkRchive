package work.racka.thinkrchive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import work.racka.thinkrchive.ui.navigation.ThinkrchiveApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge experience and ProvideWindowInsets to the composables
        WindowCompat.setDecorFitsSystemWindows(window, false)

        Timber.d("onCreate called")

        setContent {
            ThinkrchiveApp()
            Timber.d("setContent called")
        }
    }
}
