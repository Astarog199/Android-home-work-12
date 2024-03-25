package com.example.homework12.ui.main

import android.text.Editable
import android.util.Log
import android.widget.ImageButton
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class BlankViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _stateSearch: MutableStateFlow<StateSearch> = MutableStateFlow(StateSearch.Success)
    val stateSearch = _stateSearch.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    fun onClickSearch(searchString: String) {
        viewModelScope.launch {
            if (_stateSearch.value != StateSearch.Loading) {
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

    fun searchFieldValidation(text: String?, searchButton: ImageButton) {
        val length = text?.length

        if (length != null && length > 3) {
            searchButton.isEnabled = true
        } else {
            searchButton.isEnabled = false
            Log.d("MaimViewModel", "request length must be more than 3 characters")
        }
    }
}