package com.example.medishop.RoomDB.ProductDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 2, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract val dao: ProductDao?

    companion object {
        @Volatile
        var INSTANCE: ProductDatabase? = null
        @Synchronized
        fun getInstance(context: Context?): ProductDatabase? {
            if (INSTANCE == null) {
                INSTANCE =
                    databaseBuilder(context!!, ProductDatabase::class.java, "ProductDatabases")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return INSTANCE
        }
    }
}
