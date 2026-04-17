package com.example.mula.data.model

data class NotificationItem(
    val id: String,
    val type: String,
    val title: String,
    val message: String,
    val sent_at: String,
    val is_read: Boolean
)
