package com.example.mula.data.repository

import com.example.mula.data.model.Order

interface OrdersRepository {
    suspend fun get_orders(): Result<List<Order>>
    suspend fun get_order_detail(order_id: String): Result<Order>
    suspend fun submit_rating(order_id: String, rating: Int): Result<Unit>
    suspend fun reorder(order_id: String): Result<String>
}
