package com.example.mula.data.datasource

import com.example.mula.data.model.Address
import com.example.mula.data.model.Branch
import com.example.mula.data.model.CartItem
import com.example.mula.data.model.CheckoutSummary
import com.example.mula.data.model.PaymentBreakdown
import com.example.mula.data.model.Voucher

interface CheckoutDataSource {
    suspend fun add_to_cart(product_id: String, order_method: String, selected_temperature: String, selected_cup_size: String, selected_free_topping: String?, quantity: Int): Result<CartItem>
    suspend fun get_checkout_summary(order_method: String): Result<CheckoutSummary>
    suspend fun apply_voucher(voucher_id: String): Result<CheckoutSummary>
    suspend fun set_selected_branch(branch: Branch): Result<Unit>
    suspend fun set_selected_address(address: Address): Result<Unit>
    suspend fun set_order_method(order_method: String): Result<Unit>
    suspend fun set_eco_bag_enabled(is_enabled: Boolean): Result<Unit>
}

class FakeCheckoutDataSource : CheckoutDataSource {
    private val branch = Branch("branch_1", "mula_central", "1.2_km", "jalan_mula_1", "open", true)
    private val address = Address("address_1", "rumah", "jalan_kopi_12", -6.2, 106.8)
    private val voucher = Voucher("voucher_1", "hemat_10k", "potongan_qris", "2026-12-31", "fixed", 10000, 30000, 10000, true)
    private val cartItems = listOf(
        CartItem("cart_1", "product_1", "iced_latte", "ice", "regular", null, 1, 28000, 28000)
    )

    override suspend fun add_to_cart(product_id: String, order_method: String, selected_temperature: String, selected_cup_size: String, selected_free_topping: String?, quantity: Int): Result<CartItem> =
        Result.success(CartItem("cart_new", product_id, "iced_latte", selected_temperature, selected_cup_size, selected_free_topping, quantity, 28000, 28000 * quantity))

    override suspend fun get_checkout_summary(order_method: String): Result<CheckoutSummary> = Result.success(
        CheckoutSummary(
            order_method = order_method,
            branch = branch,
            address = if (order_method == "delivery") address else null,
            cart_items = cartItems,
            selected_voucher = null,
            payment_breakdown = PaymentBreakdown(28000, 2000, 3000, 0, 0, 33000),
            reward_token_earned = 28
        )
    )

    override suspend fun apply_voucher(voucher_id: String): Result<CheckoutSummary> = Result.success(
        CheckoutSummary(
            order_method = "pickup",
            branch = branch,
            address = null,
            cart_items = cartItems,
            selected_voucher = voucher,
            payment_breakdown = PaymentBreakdown(28000, 2000, 3000, 10000, 0, 23000),
            reward_token_earned = 28
        )
    )

    override suspend fun set_selected_branch(branch: Branch): Result<Unit> = Result.success(Unit)

    override suspend fun set_selected_address(address: Address): Result<Unit> = Result.success(Unit)

    override suspend fun set_order_method(order_method: String): Result<Unit> = Result.success(Unit)

    override suspend fun set_eco_bag_enabled(is_enabled: Boolean): Result<Unit> = Result.success(Unit)
}
