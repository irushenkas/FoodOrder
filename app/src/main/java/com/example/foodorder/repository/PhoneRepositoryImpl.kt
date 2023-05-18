package com.example.foodorder.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PhoneRepositoryImpl(private val context: Context,
) : PhoneRepository {

    private val gson = Gson()
    val typePhone = TypeToken.getParameterized(String::class.java).type
    private val filename = "products_to_order_phone.json"

    private var phone: String? = ""
    private val data = MutableLiveData(phone)

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use {
                phone = gson.fromJson(it, typePhone)
                data.value = phone
            }
        } else {
            sync()
        }
    }

    override fun get(): LiveData<String?> = data

    override fun save(phone: String) {
            data.value = phone
            sync()
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(data.value))
        }
    }
}