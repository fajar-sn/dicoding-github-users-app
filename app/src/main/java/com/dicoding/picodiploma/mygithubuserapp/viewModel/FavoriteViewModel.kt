package com.dicoding.picodiploma.mygithubuserapp.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.mygithubuserapp.model.User
import com.dicoding.picodiploma.mygithubuserapp.repository.local.UserLocalRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val userLocalRepository = UserLocalRepository(application)

    fun getAllUsers(): LiveData<List<User>> = userLocalRepository.getAllUsers()
}