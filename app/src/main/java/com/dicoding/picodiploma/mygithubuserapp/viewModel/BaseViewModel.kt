package com.dicoding.picodiploma.mygithubuserapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.mygithubuserapp.repository.remote.ApiConfig
import com.dicoding.picodiploma.mygithubuserapp.helper.Event

open class BaseViewModel : ViewModel(){
    protected val service = ApiConfig.getApiService()

    protected val mutableIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = mutableIsLoading

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText

    protected lateinit var tag: String

    fun onFailed(message: String) {
        _snackBarText.value = Event("Failed to fetch data. $message")
        Log.e(tag, "onFailure: $message")
    }
}