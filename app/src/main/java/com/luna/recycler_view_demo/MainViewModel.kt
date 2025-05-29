package com.luna.recycler_view_demo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val repository: Repository = Repository()
    private val geminiPromptHandler = GeminiPromptHandler()
    val repositoryItems: List<String> = repository.getItems()

    private val _newsBannerText = MutableSharedFlow<String>()
    val newsBannerText = _newsBannerText.asSharedFlow()

    fun getNewsText() {
        viewModelScope.launch {
            while(true){
                val newsText = geminiPromptHandler.getRandomNews()
                _newsBannerText.emit(newsText ?: "Error fetching news")
                delay(10000L)
            }
        }
    }
}