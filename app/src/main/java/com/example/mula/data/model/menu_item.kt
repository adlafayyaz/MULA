package com.example.mula.data.model

data class MenuItem(
    val id: String,
    val name: String,
    val description: String,
    val base_price: Int,
    val rating: Double,
    val image_res_name: String?,
    val category_id: String,
    val is_favorite: Boolean
)
