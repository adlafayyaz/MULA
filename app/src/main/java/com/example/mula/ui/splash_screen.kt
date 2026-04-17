package com.example.mula.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.brand_brown_primary
import com.example.mula.ui.theme.text_on_primary
import com.example.mula.viewmodel.SplashScreenEvent
import com.example.mula.viewmodel.SplashViewModel

data class SplashScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun SplashScreenRoute(viewModel: SplashViewModel = viewModel()) {
    SplashScreen(state = viewModel.state) { viewModel.on_event(SplashScreenEvent.RetryRequested) }
}

@Composable
fun SplashScreen(
    state: SplashScreenState,
    onRetry: () -> Unit = {}
) {
    Stage4ABackground(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(156.dp)
                    .background(brand_brown_primary, CircleShape)
                    .testTag("app_logo_image"),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "MULA", style = MaterialTheme.typography.headlineMedium, color = text_on_primary)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun SplashScreenPreview() {
    MulaTheme { SplashScreen(state = SplashScreenState()) }
}
