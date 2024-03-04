package com.example.medishop.RoomDB.CartDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartDao {
    @Insert
    fun insertCart(Cart: Cart?)

    @Update
    fun updateCart(Cart: Cart?)

    @Query("delete from Cart where cId=:cId")
    fun delete(cId: String?): Int

    @Query("delete from Cart")
    fun deleteAll(): Int

    @get:Query("Select * from Cart")
    val allCart: List<Cart?>?

    @Query("SELECT EXISTS(SELECT * FROM Cart WHERE cId = :cId)")
    fun CartExists(cId: String?): Boolean
}
