package com.example.medishop.RoomDB.UserDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract val dao: UserDAO?

    companion object {
        @Volatile
        var INSTANCE: UserDatabase? = null
        @Synchronized
        fun getInstance(context: Context?): UserDatabase? {
            if (INSTANCE == null) {
                INSTANCE = databaseBuilder(context!!, UserDatabase::class.java, "UserDatabases")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }
}
