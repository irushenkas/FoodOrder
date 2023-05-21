package com.example.foodorder.repository

import androidx.lifecycle.LiveData
import com.example.foodorder.dto.Product

interface ProductRepository {
    fun getAll(): LiveData<List<Product>>
    fun save(product: Product)
    fun removeById(id: Long)
    fun cleanTemporaryData()
}