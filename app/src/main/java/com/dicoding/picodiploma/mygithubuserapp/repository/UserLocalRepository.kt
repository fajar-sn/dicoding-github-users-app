package com.dicoding.picodiploma.mygithubuserapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.mygithubuserapp.dao.UserDao
import com.dicoding.picodiploma.mygithubuserapp.database.UserRoomDatabase
import com.dicoding.picodiploma.mygithubuserapp.model.User
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserLocalRepository(application: Application) {
    private val userDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        userDao = db.getUserDao()
    }

//    fun getAllUsers(): LiveData<List<User>> = userDao.getAllUsers()

    fun insert(user: User) = executorService.execute { userDao.insert(user) }

    fun delete(user: User) = executorService.execute { userDao.delete(user) }

    fun update(user: User) = executorService.execute { userDao.delete(user) }
}