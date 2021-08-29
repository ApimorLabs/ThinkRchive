package work.racka.thinkrchive.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import work.racka.thinkrchive.utils.Constants
import java.io.IOException

@ActivityScoped
class DataStoreRepository(
    private val context: Context
) {
    private object PreferenceKeys {
        val themeOption = intPreferencesKey(name = "theme_option")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = Constants.PREFERENCE_NAME
    )

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
            settings[PreferenceKeys.themeOption] ?: 0
        }
}