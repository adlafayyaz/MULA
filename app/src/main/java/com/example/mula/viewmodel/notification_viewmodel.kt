package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mula.data.model.NotificationItem
import com.example.mula.data.repository.NotificationRepository
import kotlinx.coroutines.launch

data class NotificationScreenState(
    val is_loading: Boolean = false,
    val error_message: String? = null,
    val notifications: List<NotificationItem> = emptyList(),
    val has_notifications: Boolean = false,
    val navigation_target: String? = null,
    val pop_back_stack: Boolean = false
)

sealed class NotificationScreenEvent {
    data object OnScreenOpened : NotificationScreenEvent()
    data object OnBackClicked : NotificationScreenEvent()
    data class OnNotificationClicked(val notification_id: String) : NotificationScreenEvent()
    data object OnRetryClicked : NotificationScreenEvent()
    data object OnNavigationHandled : NotificationScreenEvent()
}

class NotificationViewModel : ViewModel() {
    private val notification_repository: NotificationRepository = Stage5ARepositoryProvider.notification_repository

    var state by mutableStateOf(NotificationScreenState())
        private set

    fun on_event(event: NotificationScreenEvent) {
        when (event) {
            NotificationScreenEvent.OnScreenOpened,
            NotificationScreenEvent.OnRetryClicked -> load_notifications()
            NotificationScreenEvent.OnBackClicked -> state = state.copy(pop_back_stack = true)
            is NotificationScreenEvent.OnNotificationClicked -> mark_as_read(event.notification_id)
            NotificationScreenEvent.OnNavigationHandled -> state = state.copy(pop_back_stack = false, navigation_target = null)
        }
    }

    private fun load_notifications() {
        if (state.is_loading) return
        viewModelScope.launch {
            state = state.copy(is_loading = true, error_message = null)
            notification_repository.get_notifications()
                .onSuccess { notifications ->
                    state = state.copy(
                        is_loading = false,
                        notifications = notifications,
                        has_notifications = notifications.isNotEmpty()
                    )
                }
                .onFailure { throwable ->
                    state = state.copy(
                        is_loading = false,
                        error_message = throwable.message ?: "Gagal memuat notifikasi"
                    )
                }
        }
    }

    private fun mark_as_read(notification_id: String) {
        viewModelScope.launch {
            notification_repository.mark_as_read(notification_id = notification_id)
            val updated_notifications = state.notifications.map {
                if (it.id == notification_id) it.copy(is_read = true) else it
            }
            state = state.copy(
                notifications = updated_notifications,
                has_notifications = updated_notifications.isNotEmpty()
            )
        }
    }
}
