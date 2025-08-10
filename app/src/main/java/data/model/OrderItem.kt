package com.example.nascoffee3.data.model

data class OrderItem(
    val id: Int,
    val name: String,
    val details: String,
    val price: Double,
    var quantity: Int,
    val imageUrl: String
)

