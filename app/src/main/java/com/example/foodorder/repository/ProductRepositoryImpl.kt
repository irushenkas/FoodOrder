package com.example.foodorder.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodorder.dto.Product

class ProductRepositoryImpl : ProductRepository {
    private var nextId = 1L
    private var products = listOf(
        Product(
            id = nextId++,
            name = "Картошка",
        ),
        Product(
            id = nextId++,
            name = "Морковка",
        ),
    )

    private val data = MutableLiveData(products)

    override fun getAll(): LiveData<List<Product>> = data

    override fun save(product: Product) {
        if (product.id == 0L) {
            products = listOf(
                product.copy(
                    id = nextId++,
                )
            ) + products
            data.value = products
            return
        }

        products = products.map {
            if (it.id != product.id) it else it.copy(name = product.name)
        }
        data.value = products
    }

    override fun removeById(id: Long) {
        products = products.filter { it.id != id }
        data.value = products
    }
}