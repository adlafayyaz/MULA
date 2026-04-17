package com.example.mula.data.datasource

import com.example.mula.data.model.Order
import com.example.mula.data.model.OrderItem
import com.example.mula.data.model.PaymentBreakdown

interface OrdersDataSource {
    suspend fun get_orders(): Result<List<Order>>
    suspend fun get_order_detail(order_id: String): Result<Order>
    suspend fun submit_rating(order_id: String, rating: Int): Result<Unit>
    suspend fun reorder(order_id: String): Result<String>
}

class FakeOrdersDataSource : OrdersDataSource {
    private val orders = listOf(
        Order(
            id = "order_1",
            order_number = "MULA-001",
            result_status = "success",
            order_method = "pickup",
            branch_name = "mula_central",
            delivery_address = null,
            source_text = "mobile_app",
            date_text = "2026-04-17",
            time_text = "09:00",
            items = listOf(OrderItem("item_1", "iced_latte", "ice_regular", 1, 28000)),
            voucher_name = "hemat_10k",
            payment_breakdown = PaymentBreakdown(28000, 2000, 3000, 10000, 0, 23000),
            live_status = "ready_for_pickup",
            qr_code_res_name = "img_order_qr"
        ),
        Order(
            id = "order_2",
            order_number = "MULA-002",
            result_status = "failed",
            order_method = "delivery",
            branch_name = "mula_north",
            delivery_address = "jalan_kopi_12",
            source_text = "mobile_app",
            date_text = "2026-04-15",
            time_text = "18:30",
            items = listOf(OrderItem("item_2", "americano", "hot_regular", 1, 22000)),
            voucher_name = null,
            payment_breakdown = PaymentBreakdown(22000, 2000, 2000, 0, 0, 26000),
            live_status = "payment_expired",
            qr_code_res_name = null
        )
    )

    override suspend fun get_orders(): Result<List<Order>> = Result.success(orders)

    override suspend fun get_order_detail(order_id: String): Result<Order> = Result.success(orders.first { it.id == order_id })

    override suspend fun submit_rating(order_id: String, rating: Int): Result<Unit> = Result.success(Unit)

    override suspend fun reorder(order_id: String): Result<String> = Result.success("product_1")
}
