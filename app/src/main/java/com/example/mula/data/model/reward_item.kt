package com.example.mula.data.model

data class RewardItem(
    val id: String,
    val name: String,
    val token_cost: Int,
    val image_res_name: String?,
    val filter_id: String,
    val is_redeemable: Boolean
)
