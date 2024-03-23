package com.example.homework12.ui.main

sealed class StateSearch {
    data object Loading : StateSearch()
    data object Success : StateSearch()
    data class Error(
        val error: String?
    ): StateSearch()
}