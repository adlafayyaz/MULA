package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.ProfileScreenEvent
import com.example.mula.viewmodel.ProfileViewModel

data class ProfileScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun ProfileScreenRoute(viewModel: ProfileViewModel = viewModel()) {
    ProfileScreen(state = viewModel.state) { viewModel.on_event(ProfileScreenEvent.RetryRequested) }
}

@Composable
fun ProfileScreen(
    state: ProfileScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "profile_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun ProfileScreenPreview() {
    MulaTheme { ProfileScreen(state = ProfileScreenState()) }
}
