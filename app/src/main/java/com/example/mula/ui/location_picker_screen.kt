package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.LocationPickerScreenEvent
import com.example.mula.viewmodel.LocationPickerViewModel

data class LocationPickerScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun LocationPickerScreenRoute(viewModel: LocationPickerViewModel = viewModel()) {
    LocationPickerScreen(state = viewModel.state) { viewModel.on_event(LocationPickerScreenEvent.RetryRequested) }
}

@Composable
fun LocationPickerScreen(
    state: LocationPickerScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "location_picker_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun LocationPickerScreenPreview() {
    MulaTheme { LocationPickerScreen(state = LocationPickerScreenState()) }
}
