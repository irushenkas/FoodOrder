package com.example.foodorder.dto


data class Product(
    val id: Long,
    val name: String,
    var comment: String?,
    var isSelected: Boolean = false,
    var priority: Int = 0,
)