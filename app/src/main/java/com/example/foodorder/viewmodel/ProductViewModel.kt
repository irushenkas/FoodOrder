package com.example.foodorder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.foodorder.dto.Product
import com.example.foodorder.repository.ProductRepository
import com.example.foodorder.repository.ProductRepositoryImpl

private val empty = Product(
    id = 0,
    name = "",
    comment = null,
    isSelected = false,
)

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProductRepository = ProductRepositoryImpl(application)
    val data = repository.getAll()
    private val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(product: Product) {
        edited.value = product
    }

    fun changeContent(content: String, priority: Int) {
        val text = content.trim()
        edited.value = edited.value?.copy(name = text, priority = priority)
    }

    fun removeById(id: Long) = repository.removeById(id)

    fun select(product: Product) {
        val isSelected = product.isSelected
        edit(product)
        edited.value = edited.value?.copy(isSelected = !isSelected)
        save()
    }

    fun getSelected(): List<Product> {
        return data.value?.filter { it.isSelected }?: emptyList()
    }

    fun changeComment(product: Product, comment: String?) {
        edit(product)
        edited.value = edited.value?.copy(comment = comment)
        save()
    }

    fun cleanTemporaryData() {
        repository.cleanTemporaryData()
    }

    fun increasePriority(selected: List<Product>) {
        repository.increasePriority(selected)
    }
}