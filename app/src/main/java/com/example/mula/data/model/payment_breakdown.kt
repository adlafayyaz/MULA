package com.example.mula.data.model

data class PaymentBreakdown(
    val subtotal: Int,
    val service_fee: Int,
    val tax: Int,
    val discount: Int,
    val eco_bag_fee: Int,
    val total: Int
)
