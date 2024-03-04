package com.example.medishop.RoomDB.ProductDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Product(
    @JvmField @field:PrimaryKey var pId: String,
    @JvmField var pName: String,
    @JvmField var pImage: String,
    @JvmField var cId: String,
    @JvmField var pPrice: String,
    @JvmField var pDetails: String,
    @JvmField var pRate: String
) 