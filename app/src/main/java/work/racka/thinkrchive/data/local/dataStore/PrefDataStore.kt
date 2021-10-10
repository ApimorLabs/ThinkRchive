package work.racka.thinkrchive.data.local.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import work.racka.thinkrchive.di.IoDispatcher
import work.racka.thinkrchive.utils.Constants
import javax.inject.Inject
import javax.inject.Singleton

// This is provided by Hilt for easy usage throughout the app
@Singleton
class PrefDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    private object PreferenceKeys {
        val themeOption = intPreferencesKey(name = "theme_option")
        val sortOption = intPreferencesKey(name = "sort_option")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = Constants.PREFERENCE_NAME,
        scope = CoroutineScope(dispatcher)
    )

    private val dataStore = context.dataStore

    // Theme Settings
    // See Theme enum class inside ui/theme/theme.kt for data meaning
    suspend fun saveThemeSetting(value: Int) {
        DataStoreHelpers.writePreference(
            dataStore,
            preferenceKey = PreferenceKeys.themeOption,
            value = value
        )
    }

    val readThemeSetting: Flow<Int> = DataStoreHelpers.readPreference(
        dataStore,
        preferenceKey = PreferenceKeys.themeOption,
        defaultValue = -1
    )

    // Sort Option Settings
    // See Sort enum class for data meaning
    suspend fun saveSortOptionSetting(value: Int) {
        DataStoreHelpers.writePreference(
            dataStore,
            preferenceKey = PreferenceKeys.sortOption,
            value = value
        )
    }

    val readSortOptionSetting: Flow<Int> = DataStoreHelpers.readPreference(
        dataStore,
        preferenceKey = PreferenceKeys.sortOption,
        defaultValue = 0
    )
}
