package com.dicoding.picodiploma.mygithubuserapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResponse(

    @field:SerializedName("total_count")
    val totalCount: Int?,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean?,

    @field:SerializedName("items")
    val users: List<User>
) : Parcelable

@Entity
@Parcelize
data class User(

    @field:SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @field:SerializedName("gists_url")
    @ColumnInfo(name = "gists_url")
    val gistsUrl: String,

    @field:SerializedName("repos_url")
    @ColumnInfo(name = "repos_url")
    val reposUrl: String,

    @field:SerializedName("following_url")
    @ColumnInfo(name = "following_url")
    val followingUrl: String,

    @field:SerializedName("starred_url")
    @ColumnInfo(name = "starred_url")
    val starredUrl: String,

    @field:SerializedName("login")
    @ColumnInfo(name = "login")
    val login: String,

    @field:SerializedName("followers_url")
    @ColumnInfo(name = "followers_url")
    val followersUrl: String,

    @field:SerializedName("type")
    @ColumnInfo(name = "type")
    val type: String,

    @field:SerializedName("url")
    @ColumnInfo(name = "url")
    val url: String,

    @field:SerializedName("subscriptions_url")
    @ColumnInfo(name = "subscriptions_url")
    val subscriptionsUrl: String,

    @field:SerializedName("received_events_url")
    @ColumnInfo(name = "received_events_url")
    val receivedEventsUrl: String,

    @field:SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @field:SerializedName("events_url")
    @ColumnInfo(name = "events_url")
    val eventsUrl: String,

    @field:SerializedName("html_url")
    @ColumnInfo(name = "html_url")
    val htmlUrl: String,

    @field:SerializedName("site_admin")
    @ColumnInfo(name = "site_admin")
    val siteAdmin: Boolean,

    @field:SerializedName("gravatar_id")
    @ColumnInfo(name = "gravatar_id")
    val gravatarId: String,

    @field:SerializedName("node_id")
    @ColumnInfo(name = "node_id")
    val nodeId: String,

    @field:SerializedName("organizations_url")
    @ColumnInfo(name = "organizations_url")
    val organizationsUrl: String,

    @field:SerializedName("score")
    @ColumnInfo(name = "score")
    val score: Double?,

    @field:SerializedName("hireable")
    @ColumnInfo(name = "hireable")
    val hireable: Boolean?,

    @field:SerializedName("twitter_username")
    @ColumnInfo(name = "twitter_username")
    val twitterUsername: String?,

    @field:SerializedName("bio")
    @ColumnInfo(name = "bio")
    val bio: String?,

    @field:SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    val createdAt: String?,

    @field:SerializedName("blog")
    @ColumnInfo(name = "blog")
    val blog: String?,

    @field:SerializedName("public_gists")
    @ColumnInfo(name = "public_gists")
    val publicGists: Int?,

    @field:SerializedName("followers")
    @ColumnInfo(name = "followers")
    val followers: Int?,

    @field:SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    val updatedAt: String?,

    @field:SerializedName("following")
    @ColumnInfo(name = "following")
    val following: Int?,

    @field:SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String?,

    @field:SerializedName("company")
    @ColumnInfo(name = "company")
    val company: String?,

    @field:SerializedName("location")
    @ColumnInfo(name = "location")
    val location: String?,

    @field:SerializedName("public_repos")
    @ColumnInfo(name = "public_repos")
    val publicRepos: Int?,

    @field:SerializedName("email")
    @ColumnInfo(name = "email")
    val email: String?

) : Parcelable
