package com.example.medishop.RoomDB.CartDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [Cart::class], version = 1, exportSchema = false)
abstract class CartDatabase : RoomDatabase() {
    abstract val dao: CartDao?

    companion object {
        @Volatile
        var INSTANCE: CartDatabase? = null
        @Synchronized
        fun getInstance(context: Context?): CartDatabase? {
            if (INSTANCE == null) {
                INSTANCE = databaseBuilder(context!!, CartDatabase::class.java, "ProductDatabases")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }
}
