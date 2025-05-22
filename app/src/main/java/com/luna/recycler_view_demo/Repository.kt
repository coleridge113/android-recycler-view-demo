package com.luna.recycler_view_demo

class Repository {
    private val itemList: List<String> = List(100) { "Item $it" }

    fun getItems(): List<String> {
        return itemList
    }
}