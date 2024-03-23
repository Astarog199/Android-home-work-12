package com.example.homework12.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class BlankViewModel(private val repository: ProductRepository) : ViewModel() {
    init {
        Log.d(TAG, "init: $this")
    }

    private val _stateSearch = MutableStateFlow<StateSearch>(StateSearch.Success)
    val stateSearch = _stateSearch.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    fun onClickSearch(searchString: String) {
        viewModelScope.launch {

            _stateSearch.value = StateSearch.Loading
            try {
                repository.getProduct(searchString)
                _stateSearch.value = StateSearch.Success
            } catch (e: Exception) {
                _error.send(e.toString())
                _stateSearch.value = StateSearch.Error(null)
            }
        }
    }
}