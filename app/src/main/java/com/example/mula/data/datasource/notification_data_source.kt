package com.example.mula.data.datasource

import com.example.mula.data.model.NotificationItem

interface NotificationDataSource {
    suspend fun get_notifications(): Result<List<NotificationItem>>
    suspend fun mark_as_read(notification_id: String): Result<Unit>
}

class FakeNotificationDataSource : NotificationDataSource {
    private val notifications = listOf(
        NotificationItem("notif_1", "promo", "promo_baru", "diskon_qris_hari_ini", "2026-04-17 08:00", false),
        NotificationItem("notif_2", "order", "pesanan_selesai", "kopi_siap_diambil", "2026-04-16 15:30", true)
    )

    override suspend fun get_notifications(): Result<List<NotificationItem>> = Result.success(notifications)

    override suspend fun mark_as_read(notification_id: String): Result<Unit> = Result.success(Unit)
}
