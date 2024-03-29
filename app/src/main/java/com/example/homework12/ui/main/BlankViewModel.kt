package com.example.homework12.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch



@OptIn(FlowPreview::class)
class BlankViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _stateSearch: MutableStateFlow<StateSearch> = MutableStateFlow(StateSearch.Success)
    val stateSearch = _stateSearch.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()
    private val editText = MutableStateFlow("")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            editText.debounce(1000L).onEach {
                if (it.length > 3) {
                    search(it)
                }
            }.collect()
        }
    }


    fun searchFieldValidation(stringSearch: String?) {
        if (stringSearch != null) editText.value = stringSearch
    }

    private fun search(stringSearch: String?) {
        Log.d("MainBlankViewModel", "stringSearch: $stringSearch")
        viewModelScope.launch {
            if (_stateSearch.value != StateSearch.Loading) {
                _stateSearch.value = StateSearch.Loading
                try {
                        repository.getProduct(stringSearch)
                        _stateSearch.value = StateSearch.Success
                } catch (e: Exception) {
                    _error.send(e.message.toString())
                    _stateSearch.value = StateSearch.Error(null)
                }
            }
        }
    }
}