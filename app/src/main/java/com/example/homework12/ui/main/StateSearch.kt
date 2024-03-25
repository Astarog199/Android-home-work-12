package com.example.homework12.ui.main

sealed class StateSearch(
    val isLoading: Boolean = false,
    open val error: String? = null,
) {
    data object Loading : StateSearch(isLoading = true)
    data object Success : StateSearch()
    data class Error(
        override val error: String?
    ): StateSearch(error = error)
}