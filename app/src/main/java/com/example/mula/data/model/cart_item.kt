package com.example.mula.data.model

data class CartItem(
    val id: String,
    val product_id: String,
    val product_name: String,
    val selected_temperature: String,
    val selected_cup_size: String,
    val selected_free_topping: String?,
    val quantity: Int,
    val unit_price: Int,
    val total_price: Int
)
