package com.example.mula.data.model

data class CheckoutSummary(
    val order_method: String,
    val branch: Branch,
    val address: Address?,
    val cart_items: List<CartItem>,
    val selected_voucher: Voucher?,
    val payment_breakdown: PaymentBreakdown,
    val reward_token_earned: Int
)
