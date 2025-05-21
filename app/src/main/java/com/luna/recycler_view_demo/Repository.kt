package com.luna.recycler_view_demo

import android.util.Log

class Repository {
    private val itemList: List<String> = List(100) { "Item $it" }

    fun getItems(): List<String> {
        Log.d("Repository", itemList.toString())
        return itemList
    }
}