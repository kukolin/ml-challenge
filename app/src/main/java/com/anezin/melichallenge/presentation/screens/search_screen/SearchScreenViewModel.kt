package com.anezin.melichallenge.presentation.screens.search_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
) : ViewModel() {
    var textToSearch by mutableStateOf("")
        private set

    var canSearch by mutableStateOf(false)
        private set

    fun updateSearchQuery(query: String) {
        textToSearch = query
        canSearch = textToSearch.isNotEmpty()
    }

    override fun onCleared() {
        Log.d("Search Screen ViewModel", "onCleared")
        super.onCleared()
    }
}