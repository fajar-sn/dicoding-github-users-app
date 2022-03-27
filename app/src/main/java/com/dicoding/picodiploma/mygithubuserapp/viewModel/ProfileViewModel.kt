package com.dicoding.picodiploma.mygithubuserapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.mygithubuserapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : BaseViewModel() {
    val user = MutableLiveData<User>()

    private val _listUser = MutableLiveData<List<User>>()
    val listUser: LiveData<List<User>> = _listUser

    private val _isLoadingTab = MutableLiveData<Boolean>()
    val isLoadingTab: LiveData<Boolean> = _isLoadingTab

    fun getUserDetail() {
        mutableIsLoading.value = true

        user.value?.let { service.getUserDetail(it.login) }?.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                mutableIsLoading.value = false

                if (response.isSuccessful) user.value = response.body()
                else onFailed(response.message())
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                mutableIsLoading.value = false
                onFailed("${t.message}")
            }
        })
    }

    fun getListUser(isFollowers: Boolean = true) {
        if (isFollowers) updateListUser { service.getUserFollowersList(it) }
        else updateListUser { service.getUserFollowingList(it) }
    }

    private fun updateListUser(func: (username: String) -> Call<List<User>>) {
        _isLoadingTab.value = true

        user.value?.let { func(it.login) }?.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _isLoadingTab.value = false

                if (response.isSuccessful) _listUser.value = response.body()
                else onFailed(response.message())
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoadingTab.value = false
                onFailed("${t.message}")
            }
        })
    }
}