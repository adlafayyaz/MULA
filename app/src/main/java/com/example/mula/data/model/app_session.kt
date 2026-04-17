package com.example.mula.data.model

data class AppSession(
    val token: String,
    val refresh_token: String?,
    val user_id: String,
    val is_logged_in: Boolean,
    val is_first_launch: Boolean
)
