package com.example.mula.data.repository

import com.example.mula.data.model.Address
import com.example.mula.data.model.Branch
import com.example.mula.data.model.CartItem
import com.example.mula.data.model.CheckoutSummary

interface CheckoutRepository {
    suspend fun add_to_cart(product_id: String, order_method: String, selected_temperature: String, selected_cup_size: String, selected_free_topping: String?, quantity: Int): Result<CartItem>
    suspend fun get_checkout_summary(order_method: String): Result<CheckoutSummary>
    suspend fun apply_voucher(voucher_id: String): Result<CheckoutSummary>
    suspend fun set_selected_branch(branch: Branch): Result<Unit>
    suspend fun set_selected_address(address: Address): Result<Unit>
    suspend fun set_order_method(order_method: String): Result<Unit>
    suspend fun set_eco_bag_enabled(is_enabled: Boolean): Result<Unit>
}
