package com.dicoding.picodiploma.mygithubuserapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.mygithubuserapp.model.SearchResponse
import com.dicoding.picodiploma.mygithubuserapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : BaseViewModel() {
    private val _listUser = MutableLiveData<List<User>>()
    val listUser: LiveData<List<User>> = _listUser

    init {
        tag = "MainViewModel"
        getListUser()
    }

    private fun getListUser() {
        mutableIsLoading.value = true
        val client = service.getUsers()

        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                mutableIsLoading.value = false

                if (response.isSuccessful)
                    _listUser.value = response.body()
                else
                    onFailed(response.message())
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                mutableIsLoading.value = false
                onFailed("${t.message}")
            }
        })
    }

    fun searchUser(username: String) {
        mutableIsLoading.value = true
        val client = service.searchUser(username)

        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                mutableIsLoading.value = false

                if (response.isSuccessful)
                    _listUser.value = response.body()?.users
                else
                    onFailed(response.message())
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                mutableIsLoading.value = false
                onFailed("${t.message}")
            }
        })
    }
}