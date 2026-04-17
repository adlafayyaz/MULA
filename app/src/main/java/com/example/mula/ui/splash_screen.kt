package com.example.mula.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.R
import com.example.mula.navigation.ROUTE_AUTH_LANDING
import com.example.mula.navigation.ROUTE_HOME
import com.example.mula.navigation.ROUTE_ONBOARDING
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.SplashScreenEvent
import com.example.mula.viewmodel.SplashViewModel
import kotlinx.coroutines.delay

data class SplashScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun SplashScreenRoute(
    has_seen_onboarding: Boolean = false,
    is_logged_in: Boolean = false,
    on_splash_complete: (String) -> Unit = {},
    viewModel: SplashViewModel = viewModel()
) {
    SplashScreen(
        state = SplashScreenState(
            is_loading = viewModel.state.is_loading,
            error_message = viewModel.state.error_message
        ),
        has_seen_onboarding = has_seen_onboarding,
        is_logged_in = is_logged_in,
        on_splash_complete = on_splash_complete
    ) { viewModel.on_event(SplashScreenEvent.OnRetryClicked) }
}

@Composable
fun SplashScreen(
    state: SplashScreenState,
    has_seen_onboarding: Boolean = false,
    is_logged_in: Boolean = false,
    on_splash_complete: (String) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    LaunchedEffect(has_seen_onboarding, is_logged_in) {
        delay(700)
        on_splash_complete(
            when {
                !has_seen_onboarding -> ROUTE_ONBOARDING
                !is_logged_in -> ROUTE_AUTH_LANDING
                else -> ROUTE_HOME
            }
        )
    }
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFFFFFFF))) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.ic_splash),
                contentDescription = null,
                modifier = Modifier
                    .size(170.dp)
                    .testTag("app_logo_image")
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun SplashScreenPreview() {
    MulaTheme { SplashScreen(state = SplashScreenState()) }
}
