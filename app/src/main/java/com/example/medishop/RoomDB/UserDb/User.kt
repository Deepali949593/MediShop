package com.example.medishop.RoomDB.UserDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(@kotlin.jvm.JvmField var email: String, var name: String, @field:PrimaryKey var phone: String)
