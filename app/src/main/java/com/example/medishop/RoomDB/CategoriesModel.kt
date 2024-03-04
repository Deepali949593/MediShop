package com.example.medishop.RoomDB

class CategoriesModel(private var cImage: String, var title: String, var details: String) {

    fun getcImage(): String {
        return cImage
    }

    fun setcImage(cImage: String) {
        this.cImage = cImage
    }
}