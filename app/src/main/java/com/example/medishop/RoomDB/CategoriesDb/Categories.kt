package com.example.medishop.RoomDB.CategoriesDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Categories(@JvmField @field:PrimaryKey var cId: String, @JvmField var cName: String, @JvmField var cImage: String)