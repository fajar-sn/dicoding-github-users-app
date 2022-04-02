package com.dicoding.picodiploma.mygithubuserapp.view.settings

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.mygithubuserapp.R
import com.dicoding.picodiploma.mygithubuserapp.database.SettingsPreferences
import com.dicoding.picodiploma.mygithubuserapp.databinding.ActivitySettingsBinding
import com.dicoding.picodiploma.mygithubuserapp.helper.ActivityHelper
import com.dicoding.picodiploma.mygithubuserapp.helper.ViewModelFactory
import com.dicoding.picodiploma.mygithubuserapp.viewModel.SettingsViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Settings"
        val switchTheme = binding.switchMaterialTheme
        val preferences = SettingsPreferences.getInstance(dataStore)
        val viewModel = ActivityHelper.obtainViewModel<SettingsViewModel>(this, preferences)

        viewModel.getThemeSettings().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }
    }
}