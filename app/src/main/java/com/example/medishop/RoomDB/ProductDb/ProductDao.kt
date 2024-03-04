package com.example.medishop.RoomDB.ProductDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {
    @Insert
    fun insertProduct(Product: Product?)

    @Update
    fun updateProduct(Product: Product?)

    @Query("delete from Product where cId=:cId")
    fun delete(cId: String?): Int

    @Query("delete from Product")
    fun deleteAll(): Int

    @get:Query("Select * from Product")
    val allProduct: List<Product?>?

    @Query("SELECT EXISTS(SELECT * FROM Product WHERE cId = :cId)")
    fun ProductExists(cId: String?): Boolean
}
