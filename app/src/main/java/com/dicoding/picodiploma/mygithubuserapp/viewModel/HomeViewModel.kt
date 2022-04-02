package com.dicoding.picodiploma.mygithubuserapp.viewModel

import androidx.lifecycle.asLiveData
import com.dicoding.picodiploma.mygithubuserapp.database.SettingsPreferences
import com.dicoding.picodiploma.mygithubuserapp.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val preferences: SettingsPreferences) : ListUserViewModel() {

    init {
        tag = "MainViewModel"
        getListUser()
    }

    fun getThemeSettings() = preferences.getThemeSettings().asLiveData()

    private fun getListUser() = super.getListUser { service.getUsers() }

    fun searchUser(username: String) {
        mutableIsLoading.value = true
        val client = service.searchUser(username)

        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>,
            ) {
                mutableIsLoading.value = false

                if (response.isSuccessful)
                    mutableListUser.value = response.body()?.users
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