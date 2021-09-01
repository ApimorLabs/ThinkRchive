package work.racka.thinkrchive

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import work.racka.thinkrchive.repository.DataStoreRepository
import work.racka.thinkrchive.ui.navigation.ThinkrchiveApp
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository


    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        // Enable edge-to-edge experience and ProvideWindowInsets to the composables
        WindowCompat.setDecorFitsSystemWindows(window, false)

        Timber.d("onCreate called")

        setContent {
            val themeValue = remember {
                mutableStateOf(-1)
            }
            LaunchedEffect(key1 = themeValue) {
                dataStoreRepository.readThemeSetting.collect {
                    themeValue.value = it
                }
            }
            ThinkrchiveApp(themeValue.value)
            Timber.d("setContent called")
        }
    }
}
