package com.example.homework12.ui.main

import kotlinx.coroutines.delay
import java.lang.Exception

class ProductRepository {

    suspend fun getProduct(arg: String?):  Product? {
       delay(5000)
        return if (false){
            null
        }else{
            throw Exception("По запросу <$arg> ничего не найдено")
        }
    }
}

abstract class Product{
    val id: String? = null
    var title:String? = null
    var price:Int = 0
    var description:String? = null
}