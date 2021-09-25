package work.racka.thinkrchive.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import work.racka.thinkrchive.utils.Constants
import java.io.IOException
import javax.inject.Inject

// This is provided by Hilt for easy usage throughout the app
// See @DataStoreModule
@ActivityScoped
class DataStoreRepository @Inject constructor(
    private val context: Context,
    dataStoreScope: CoroutineScope
) {
    private object PreferenceKeys {
        val themeOption = intPreferencesKey(name = "theme_option")
        val sortOption = intPreferencesKey(name = "sort_option")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = Constants.PREFERENCE_NAME,
        scope = dataStoreScope
    )

    // Theme Settings
    // See Theme enum class for data meaning
    suspend fun saveThemeSetting(value: Int) {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.themeOption] = value
        }
    }

    val readThemeSetting: Flow<Int> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.d(exception.message.toString())
            } else {
                throw exception
            }
        }.map { settings ->
            settings[PreferenceKeys.themeOption] ?: -1
        }

    // Sort Option Settings
    // See Sort enum class for data meaning
    suspend fun saveSortOptionSetting(value: Int) {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.sortOption] = value
        }
    }

    val readSortOptionSetting: Flow<Int> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.d(exception.message.toString())
            } else {
                throw exception
            }
        }.map { settings ->
            settings[PreferenceKeys.sortOption] ?: 0
        }
}
