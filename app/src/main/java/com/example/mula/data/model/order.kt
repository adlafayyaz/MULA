package com.example.mula.data.model

data class Order(
    val id: String,
    val order_number: String,
    val result_status: String,
    val order_method: String,
    val branch_name: String,
    val delivery_address: String?,
    val source_text: String,
    val date_text: String,
    val time_text: String,
    val items: List<OrderItem>,
    val voucher_name: String?,
    val payment_breakdown: PaymentBreakdown,
    val live_status: String?,
    val qr_code_res_name: String?
)
