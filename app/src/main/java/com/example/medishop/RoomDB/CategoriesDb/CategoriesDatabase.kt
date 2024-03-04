package com.example.medishop.RoomDB.CategoriesDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [Categories::class], version = 1, exportSchema = false)
abstract class CategoriesDatabase : RoomDatabase() {
    abstract val dao: CategoriesDao?

    companion object {
        @Volatile
        var INSTANCE: CategoriesDatabase? = null
        @Synchronized
        fun getInstance(context: Context?): CategoriesDatabase? {
            if (INSTANCE == null) {
                INSTANCE = databaseBuilder(
                    context!!,
                    CategoriesDatabase::class.java,
                    "CategoriesDatabases"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }
}
