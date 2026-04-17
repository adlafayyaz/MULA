package com.example.mula.data.repository

import com.example.mula.data.model.NotificationItem

interface NotificationRepository {
    suspend fun get_notifications(): Result<List<NotificationItem>>
    suspend fun mark_as_read(notification_id: String): Result<Unit>
}
