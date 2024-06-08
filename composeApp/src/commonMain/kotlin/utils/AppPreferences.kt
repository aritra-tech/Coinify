package utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface AppPreferences {

    suspend fun isDarkModeEnabled(): Boolean
    suspend fun changeDarkMode(isEnabled: Boolean) : Preferences

    val onDarkModeChange: Flow<Boolean>
}

internal class AppPreferencesImpl(
    private val dataStore: DataStore<Preferences>
): AppPreferences {

    private companion object {
        private const val PREFS_TAG_KEY = "AppPreferences"
        private const val IS_DARK_MODE_ENABLED = "prefsBoolean"
    }

    private val darkModeKey = booleanPreferencesKey("$PREFS_TAG_KEY$IS_DARK_MODE_ENABLED")

    override suspend fun isDarkModeEnabled() = dataStore.data.map { preferences ->
        preferences[darkModeKey] ?: false
    }.first()

    override suspend fun changeDarkMode(isEnabled : Boolean) = dataStore.edit { preferences ->
        preferences[darkModeKey] = isEnabled
    }

    override val onDarkModeChange: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[darkModeKey] ?: false
        }
}