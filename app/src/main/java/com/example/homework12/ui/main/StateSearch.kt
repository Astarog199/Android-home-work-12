package com.example.homework12.ui.main

sealed class StateSearch(
    open val error: String? = null,
) {
    data object Loading : StateSearch()
    data object Success : StateSearch()
    data class Error(
        override val error: String?
    ): StateSearch(error = error)
}