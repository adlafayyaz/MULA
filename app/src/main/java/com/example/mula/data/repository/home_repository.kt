package com.example.mula.data.repository

import com.example.mula.data.model.Banner
import com.example.mula.data.model.Promo

interface HomeRepository {
    suspend fun get_banners(): Result<List<Banner>>
    suspend fun get_promos(): Result<List<Promo>>
    suspend fun get_token_balance(): Result<Int>
    suspend fun get_greeting_name(): Result<String>
}
