package com.example.mula.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.components.PrimaryButton
import com.example.mula.ui.components.SecondaryTextLink
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.AuthLandingScreenEvent
import com.example.mula.viewmodel.AuthLandingViewModel

data class AuthLandingScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun AuthLandingScreenRoute(viewModel: AuthLandingViewModel = viewModel()) {
    AuthLandingScreen(state = viewModel.state) { viewModel.on_event(AuthLandingScreenEvent.RetryRequested) }
}

@Composable
fun AuthLandingScreen(
    state: AuthLandingScreenState,
    onRetry: () -> Unit = {}
) {
    Stage4ABackground(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(stage_4a_screen_padding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(mula_spacing.lg.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                ArtworkPlaceholder(
                    label = "Hero Coffee",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    test_tag = "auth_hero_image_pager"
                )
                Text(
                    text = "Masuk dan nikmati promo kopi favoritmu.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = body_text_color,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = mula_spacing.md.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp)
            ) {
                PrimaryButton(
                    text = "Masuk",
                    on_click = onRetry,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("login_button")
                )
                SecondaryTextLink(
                    text = "Belum punya akun? Daftar",
                    on_click = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("register_link_button"),
                    text_align = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun AuthLandingScreenPreview() {
    MulaTheme { AuthLandingScreen(state = AuthLandingScreenState()) }
}
