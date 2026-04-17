package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.NotificationScreenEvent
import com.example.mula.viewmodel.NotificationViewModel

data class NotificationScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun NotificationScreenRoute(viewModel: NotificationViewModel = viewModel()) {
    NotificationScreen(state = viewModel.state) { viewModel.on_event(NotificationScreenEvent.RetryRequested) }
}

@Composable
fun NotificationScreen(
    state: NotificationScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "notification_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun NotificationScreenPreview() {
    MulaTheme { NotificationScreen(state = NotificationScreenState()) }
}
