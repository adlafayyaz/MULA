package com.example.mula.data.datasource

import com.example.mula.data.model.Banner
import com.example.mula.data.model.Promo

interface HomeDataSource {
    suspend fun get_banners(): Result<List<Banner>>
    suspend fun get_promos(): Result<List<Promo>>
    suspend fun get_token_balance(): Result<Int>
    suspend fun get_greeting_name(): Result<String>
}

class FakeHomeDataSource : HomeDataSource {
    override suspend fun get_banners(): Result<List<Banner>> = Result.success(
        listOf(
            Banner("banner_1", "mula_weekend", "buy_1_get_1", "img_banner_weekend", "route", "voucher_screen"),
            Banner("banner_2", "coffee_time", "free_delivery", "img_banner_delivery", "route", "catalog_screen")
        )
    )

    override suspend fun get_promos(): Result<List<Promo>> = Result.success(
        listOf(
            Promo("promo_1", "diskon_qris", "hemat_harian", "img_promo_qris", true),
            Promo("promo_2", "free_topping", "khusus_pickup", "img_promo_topping", true)
        )
    )

    override suspend fun get_token_balance(): Result<Int> = Result.success(240)

    override suspend fun get_greeting_name(): Result<String> = Result.success("Mula User")
}
