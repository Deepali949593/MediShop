package com.example.medishop.RoomDB.CategoriesDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CategoriesDao {
    @Insert
    fun insertCategories(categories: Categories?)

    @Update
    fun updateCategories(categories: Categories?)

    @Query("delete from Categories where cId=:cId")
    fun delete(cId: String?): Int

    @Query("delete from Categories")
    fun deleteAll(): Int

    @get:Query("Select * from Categories")
    val allCategories: List<Categories?>?

    @Query("SELECT EXISTS(SELECT * FROM Categories WHERE cId = :cId)")
    fun CategoriesExists(cId: String?): Boolean
}
