package com.dicoding.picodiploma.mygithubuserapp.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.mygithubuserapp.database.SettingsPreferences
import com.dicoding.picodiploma.mygithubuserapp.model.User
import com.dicoding.picodiploma.mygithubuserapp.viewModel.FavoriteViewModel
import com.dicoding.picodiploma.mygithubuserapp.viewModel.HomeViewModel
import com.dicoding.picodiploma.mygithubuserapp.viewModel.ProfileViewModel
import com.dicoding.picodiploma.mygithubuserapp.viewModel.SettingsViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(
    private var application: Application?,
    private var user: User?,
    private var preferences: SettingsPreferences?,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) return user?.let { user ->
            application?.let { ProfileViewModel(user, it) }
        } as T

        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) return application?.let {
            FavoriteViewModel(it)
        } as T

        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) return preferences?.let {
            SettingsViewModel(it)
        } as T

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) return preferences?.let {
            HomeViewModel(it)
        } as T

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application, user: User?, preferences: SettingsPreferences?): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application, user, preferences)
                }
            }

            INSTANCE?.application = application
            INSTANCE?.user = user
            INSTANCE?.preferences = preferences
            return INSTANCE as ViewModelFactory
        }
    }
}