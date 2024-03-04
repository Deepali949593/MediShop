package com.example.medishop.RoomDB.UserDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDAO {
    @Insert
    fun insertUser(users: User?)

    @Update
    fun updateUser(users: User?)

    @Query(" delete from User where phone=:phone")
    fun delete(phone: String?): Int

    @get:Query("Select * from User")
    val allUsers: List<User?>?

    @Query("SELECT EXISTS(SELECT * FROM User WHERE phone = :phone)")
    fun userExists(phone: String?): Boolean

    @Query("SELECT * FROM User WHERE phone = :phone")
    fun getUserDetails(phone: String?): User?
}
