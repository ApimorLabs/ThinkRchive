package work.racka.thinkrchive

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import work.racka.thinkrchive.repository.DataStoreRepository
import javax.inject.Inject

@HiltAndroidApp
class ThinkrchiveApplication: Application() {
    @Inject lateinit var dataStoreRepository: DataStoreRepository
    override fun onCreate() {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        scope.launch {
            dataStoreRepository.readThemeSetting.collect {
                AppCompatDelegate.setDefaultNightMode(it)
            }
        }
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}