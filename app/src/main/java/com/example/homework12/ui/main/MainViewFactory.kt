package com.example.homework12.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Если класс модели, который нужно создать, является подклассом MainViewModel,
 * то создается экземпляр MainViewModel с помощью конструктора,
 * который принимает MainRepository в качестве аргумента. Если класс модели не является подклассом MainViewModel,
 * то выбрасывается исключение IllegalAccessError с сообщением "Unknown class name".
 */

class MainViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BlankViewModel::class.java)){
            return BlankViewModel(ProductRepository()) as T
        }
        throw IllegalAccessError("Unknown class name")
    }
}