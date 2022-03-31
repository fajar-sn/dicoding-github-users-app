package com.dicoding.picodiploma.mygithubuserapp.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.mygithubuserapp.model.User
import com.dicoding.picodiploma.mygithubuserapp.viewModel.FavoriteViewModel
import com.dicoding.picodiploma.mygithubuserapp.viewModel.ProfileViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(
    private val application: Application,
    private var user: User?,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) return user?.let {
            ProfileViewModel(it, application)
        } as T
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) return FavoriteViewModel(
            application) as T
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application, user: User?): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application, user)
                }
            }

            INSTANCE?.user = user
            return INSTANCE as ViewModelFactory
        }
    }
}