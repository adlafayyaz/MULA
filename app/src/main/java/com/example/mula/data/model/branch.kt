package com.example.mula.data.model

data class Branch(
    val id: String,
    val name: String,
    val distance_text: String,
    val address_text: String,
    val open_status_text: String,
    val is_open: Boolean
)
