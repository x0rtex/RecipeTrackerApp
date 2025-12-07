package com.x0rtex.recipetrackerapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.x0rtex.recipetrackerapp.data.models.SettingsKeys
import com.x0rtex.recipetrackerapp.data.models.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {
    // Define keys for preferences
    private val isDarkThemeKey = booleanPreferencesKey(name = SettingsKeys.IS_DARK_THEME)
    private val isFirstLaunchKey = booleanPreferencesKey(name = SettingsKeys.IS_FIRST_LAUNCH)
    private val defaultServingsKey = intPreferencesKey(name = SettingsKeys.DEFAULT_SERVINGS)

    // Read settings from DataStore
    val settingsFlow: Flow<UserSettings> = context.dataStore.data.map { preferences ->
        UserSettings(
            isDarkTheme = preferences[isDarkThemeKey] ?: false,
            isFirstLaunch = preferences[isFirstLaunchKey] ?: true,
            defaultServings = preferences[defaultServingsKey] ?: 4
        )
    }

    // Save theme to DataStore
    suspend fun setDarkTheme(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[isDarkThemeKey] = isDark
        }
    }

    // Save first launch to DataStore
    suspend fun setFirstLaunchComplete() {
        context.dataStore.edit { preferences ->
            preferences[isFirstLaunchKey] = false
        }
    }

    // Reset first launch to default
    suspend fun resetFirstLaunch() {
        context.dataStore.edit { preferences ->
            preferences[isFirstLaunchKey] = true
        }
    }

    // Save default servings to DataStore
    suspend fun setDefaultServings(servings: Int) {
        context.dataStore.edit { preferences ->
            preferences[defaultServingsKey] = servings
        }
    }
}
