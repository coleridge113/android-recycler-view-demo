package com.luna.recycler_view_demo

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val repository: Repository = Repository()
    val repositoryItems: List<String> = repository.getItems()
}