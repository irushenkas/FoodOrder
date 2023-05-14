package com.example.foodorder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodorder.dto.Product
import com.example.foodorder.repository.ProductRepository
import com.example.foodorder.repository.ProductRepositoryImpl


private val empty = Product(
    id = 0,
    name = "",
)

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProductRepository = ProductRepositoryImpl(application)
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(product: Product) {
        edited.value = product
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.name == text) {
            return
        }
        edited.value = edited.value?.copy(name = text)
    }

    fun removeById(id: Long) = repository.removeById(id)
}