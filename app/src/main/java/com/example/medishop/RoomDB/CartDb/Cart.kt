package com.example.medishop.RoomDB.CartDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Cart(
    @JvmField @field:PrimaryKey var cId: String,
    @JvmField var cName: String,
    @JvmField var cImage: String,
    @JvmField var cPrice: String,
    @JvmField var cDetails: String,
    @JvmField var cRate: String,
    @JvmField var cQty: String
)