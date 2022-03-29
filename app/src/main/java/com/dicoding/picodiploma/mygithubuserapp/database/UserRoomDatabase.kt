package com.dicoding.picodiploma.mygithubuserapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.picodiploma.mygithubuserapp.dao.UserDao
import com.dicoding.picodiploma.mygithubuserapp.model.User

@Database(entities = [User::class], version = 1)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        fun getDatabase(context: Context): UserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(UserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserRoomDatabase::class.java,
                        "user_database"
                    ).build()
                }
            }

            return INSTANCE as UserRoomDatabase
        }
    }
}