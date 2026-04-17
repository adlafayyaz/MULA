package com.example.mula.data.model

data class PaymentInvoice(
    val payment_id: String,
    val invoice_number: String,
    val invoice_date: String,
    val total_amount: Int,
    val status: String,
    val qr_code_res_name: String,
    val expires_at: String
)
