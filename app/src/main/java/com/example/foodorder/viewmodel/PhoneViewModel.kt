package com.example.foodorder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.foodorder.repository.PhoneRepository
import com.example.foodorder.repository.PhoneRepositoryImpl

private const val empty = ""

class PhoneViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PhoneRepository = PhoneRepositoryImpl(application)
    val data = repository.get()
    private val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(phone: String) {
        edited.value = phone
    }

    fun get(): String? {
        return data.value
    }
}