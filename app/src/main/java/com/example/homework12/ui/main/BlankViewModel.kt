package com.example.homework12.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class BlankViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _stateSearch: MutableStateFlow<StateSearch> = MutableStateFlow(StateSearch.Success)
    val stateSearch = _stateSearch.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()


    @OptIn(FlowPreview::class)
    fun searchFieldValidation(stringSearch: String?) {
            val length = stringSearch?.length
            if (length != null && length > 3) {
                viewModelScope.launch {
                    if (_stateSearch.value != StateSearch.Loading) {
                        _stateSearch.value = StateSearch.Loading
                        try {
                            flow {
                                emit(stringSearch)
                            }.debounce(300).collect{
                                repository.getProduct(it)
                                _stateSearch.value = StateSearch.Success
                            }
                        } catch (e: Exception) {
                            _error.send(e.message.toString())
                            _stateSearch.value = StateSearch.Error(null)
                        }
                    }
                }
            }
        }
}