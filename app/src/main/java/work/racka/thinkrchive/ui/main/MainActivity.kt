package work.racka.thinkrchive.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import work.racka.thinkrchive.data.local.dataStore.PrefDataStore
import work.racka.thinkrchive.ui.navigation.ThinkrchiveApp
import javax.inject.Inject

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var prefDataStore: PrefDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        // Enable edge-to-edge experience and ProvideWindowInsets to the composable
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val themeValue = remember {
                mutableStateOf(-1)
            }
            LaunchedEffect(key1 = themeValue) {
                prefDataStore.readThemeSetting.collect {
                    themeValue.value = it
                }
            }
            ThinkrchiveApp(themeValue.value)
            Timber.d("setContent called")
        }
    }
}
