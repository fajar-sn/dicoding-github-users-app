package com.dicoding.picodiploma.mygithubuserapp.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.mygithubuserapp.model.User
import com.dicoding.picodiploma.mygithubuserapp.repository.local.UserLocalRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : ViewModel() {
    private val userLocalRepository = UserLocalRepository(application)

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> = _userList

    init {
        getAllUser()
    }

    private fun getAllUser() = viewModelScope.launch {
        val userListFlow = userLocalRepository.getAllUsers()

        userListFlow.collect { _userList.value = it }
    }
}