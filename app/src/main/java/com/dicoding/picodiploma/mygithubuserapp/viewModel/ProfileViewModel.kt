package com.dicoding.picodiploma.mygithubuserapp.viewModel

import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.mygithubuserapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : BaseViewModel() {
    val user = MutableLiveData<User>()

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
}