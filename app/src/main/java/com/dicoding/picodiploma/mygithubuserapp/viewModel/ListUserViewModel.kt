package com.dicoding.picodiploma.mygithubuserapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.mygithubuserapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class ListUserViewModel : BaseViewModel() {
    val user = MutableLiveData<User>()

    protected val mutableListUser = MutableLiveData<List<User>>()
    val listUser: LiveData<List<User>> = mutableListUser

    fun getListUser(func: () -> Call<List<User>>?) {
        mutableIsLoading.value = true

        func()?.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                mutableIsLoading.value = false

                if (response.isSuccessful) mutableListUser.value = response.body()
                else onFailed(response.message())
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                mutableIsLoading.value = false
                onFailed("${t.message}")
            }
        })
    }
}

class FollowingViewModel : ListUserViewModel() {
    fun getListUser() {
        super.getListUser { user.value?.let { service.getUserFollowingList(it.login) } }
    }
}

class FollowersViewModel : ListUserViewModel() {
    fun getListUser() {
        super.getListUser { user.value?.let { service.getUserFollowersList(it.login) } }
    }
}