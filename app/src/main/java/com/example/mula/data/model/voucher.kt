package com.example.mula.data.model

data class Voucher(
    val id: String,
    val name: String,
    val description: String,
    val expiry_date: String,
    val discount_type: String,
    val discount_value: Int,
    val minimum_purchase: Int?,
    val maximum_discount: Int?,
    val is_available: Boolean
)
