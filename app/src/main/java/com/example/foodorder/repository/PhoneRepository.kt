package com.example.foodorder.repository

import androidx.lifecycle.LiveData

interface PhoneRepository {
    fun get(): LiveData<String?>
    fun save(phone: String)
}