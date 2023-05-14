package com.example.foodorder.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodorder.dto.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductRepositoryImpl(private val context: Context,
) : ProductRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Product::class.java).type
    private val filename = "products_to_order.json"
    private var nextId = 0L
    private var products = emptyList<Product>()
    private val data = MutableLiveData(products)

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use {
                products = gson.fromJson(it, type)
                data.value = products
                nextId = (products.maxOfOrNull { it.id } ?: 0) + 1
            }
        } else {
            sync()
        }
    }

    override fun getAll(): LiveData<List<Product>> = data

    override fun save(product: Product) {
        if (product.id == 0L) {
            products = listOf(
                product.copy(
                    id = nextId++,
                )
            ) + products
            data.value = products
            sync()
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
        sync()
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(products))
        }
    }
}