package com.dicoding.picodiploma.mygithubuserapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.picodiploma.mygithubuserapp.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

//    @Query("SELECT * FROM notes ORDER BY id ASC")
//    fun getAllUsers(): LiveData<List<User>>
}