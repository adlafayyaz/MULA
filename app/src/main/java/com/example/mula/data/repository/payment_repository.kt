package com.example.mula.data.repository

import com.example.mula.data.model.PaymentInvoice
import com.example.mula.data.model.PaymentMethod

interface PaymentRepository {
    suspend fun get_payment_methods(): Result<List<PaymentMethod>>
    suspend fun create_qris_payment(payment_method_id: String): Result<PaymentInvoice>
    suspend fun get_payment_status(payment_id: String): Result<Pair<String, String>>
}
