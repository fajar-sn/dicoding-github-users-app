package com.dicoding.picodiploma.mygithubuserapp.repository

import com.dicoding.picodiploma.mygithubuserapp.model.SearchResponse
import com.dicoding.picodiploma.mygithubuserapp.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    @Headers("Authorization: token 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")
    fun getUsers() : Call<List<User>>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun searchUser(@Query("q") username: String) : Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(@Path("username") username: String) : Call<User>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowingList(@Path("username") username: String) : Call<List<User>>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowersList(@Path("username") username: String) : Call<List<User>>
}