package com.example.mula.data.datasource

import com.example.mula.data.model.PaymentInvoice
import com.example.mula.data.model.PaymentMethod

interface PaymentDataSource {
    suspend fun get_payment_methods(): Result<List<PaymentMethod>>
    suspend fun create_qris_payment(payment_method_id: String): Result<PaymentInvoice>
    suspend fun get_payment_status(payment_id: String): Result<Pair<String, String>>
}

class FakePaymentDataSource : PaymentDataSource {
    override suspend fun get_payment_methods(): Result<List<PaymentMethod>> = Result.success(
        listOf(PaymentMethod("payment_1", "QRIS", "qris", true))
    )

    override suspend fun create_qris_payment(payment_method_id: String): Result<PaymentInvoice> = Result.success(
        PaymentInvoice("payment_1", "INV-001", "2026-04-17", 33000, "pending", "img_qris_invoice", "2026-04-17 23:59")
    )

    override suspend fun get_payment_status(payment_id: String): Result<Pair<String, String>> = Result.success("pending" to "order_1")
}
