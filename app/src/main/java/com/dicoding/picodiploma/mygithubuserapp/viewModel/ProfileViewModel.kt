package com.dicoding.picodiploma.mygithubuserapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.mygithubuserapp.model.User
import com.dicoding.picodiploma.mygithubuserapp.repository.local.UserLocalRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(user: User, application: Application) : BaseViewModel() {
    private val _user = MutableLiveData<User>()
    val userLiveData: LiveData<User> = _user

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val userLocalRepository = UserLocalRepository(application)

    init {
        _user.value = user
        getUserDetail()
        checkIsFavorite()
    }

    private fun getUserDetail() {
        mutableIsLoading.value = true

        userLiveData.value?.let { service.getUserDetail(it.login) }
            ?.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    mutableIsLoading.value = false

                    if (response.isSuccessful) {
                        _user.value = response.body()
                    } else onFailed(response.message())
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    mutableIsLoading.value = false
                    onFailed("${t.message}")
                }
            })
    }

    fun insertToFavorite() {
        userLiveData.value?.let { userLocalRepository.insert(it) }
        checkIsFavorite()
    }

    fun deleteFromFavorite() {
        userLiveData.value?.let { userLocalRepository.delete(it) }
        checkIsFavorite()
    }

    private fun checkIsFavorite() = viewModelScope.launch {
        val userFlow =
            userLiveData.value?.let { userLocalRepository.checkIsFavorite(it.id) }

        userFlow?.collect { _isFavorite.value = it != null }

    }

}