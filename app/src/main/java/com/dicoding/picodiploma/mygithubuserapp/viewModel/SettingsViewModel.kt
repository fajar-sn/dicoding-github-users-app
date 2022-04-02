package com.dicoding.picodiploma.mygithubuserapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.mygithubuserapp.database.SettingsPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(private val preferences: SettingsPreferences): ViewModel() {
    fun getThemeSettings() = preferences.getThemeSettings().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) = viewModelScope.launch {
        preferences.saveThemeSetting(isDarkModeActive)
    }
}