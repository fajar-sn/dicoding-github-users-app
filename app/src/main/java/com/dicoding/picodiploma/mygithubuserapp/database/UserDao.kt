package com.dicoding.picodiploma.mygithubuserapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.picodiploma.mygithubuserapp.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    fun checkIsFavorite(id: Int): Flow<User>
//    fun checkIsFavorite(id: Int): LiveData<User>

//    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
//    fun testCheckIsFavorite(id: Int): Flow<User>
}