package com.example.mula.data.model

data class OrderItem(
    val id: String,
    val product_name: String,
    val variant_text: String,
    val quantity: Int,
    val price: Int
)
