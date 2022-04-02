package com.dicoding.picodiploma.mygithubuserapp.database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map

class SettingsPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val THEME_KEY = booleanPreferencesKey("theme_settings")

    fun getThemeSettings() = dataStore.data.map { it[THEME_KEY] ?: false }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) =
        dataStore.edit { it[THEME_KEY] = isDarkModeActive }

    companion object {
        @Volatile
        private var INSTANCE: SettingsPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>) =
            INSTANCE ?: synchronized(this) {
                val instance = SettingsPreferences(dataStore)
                INSTANCE = instance
                instance
            }
    }
}