package com.example.mula.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mula.ui.components.PrimaryButton
import com.example.mula.ui.theme.MulaDimens
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.app_background_color
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.muted_text_color
import com.example.mula.ui.theme.screen_surface_color

interface ScreenStateContract {
    val is_loading: Boolean
    val error_message: String?
}

@Composable
fun ScreenScaffold(
    screenName: String,
    state: ScreenStateContract,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(app_background_color)
            .padding(horizontal = MulaDimens.screen_horizontal_padding)
            .padding(vertical = mula_spacing.xl.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(screen_surface_color, MulaShapeTokens.large)
                .padding(MulaDimens.screen_inner_padding),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            Text(text = screenName, style = MaterialTheme.typography.headlineMedium, color = body_text_color)
            Text(text = "stage_2_skeleton", style = MaterialTheme.typography.bodyMedium, color = muted_text_color)
            Text(
                text = if (state.is_loading) "loading" else "idle",
                style = MaterialTheme.typography.labelMedium,
                color = muted_text_color
            )
            Text(
                text = state.error_message ?: "no_error",
                style = MaterialTheme.typography.bodySmall,
                color = muted_text_color,
                textAlign = TextAlign.Start
            )
            PrimaryButton(
                text = "retry_placeholder",
                on_click = onRetry,
                enabled = !state.is_loading
            )
        }
    }
}

@Composable
fun ScreenPreviewContainer(content: @Composable () -> Unit) {
    MulaTheme(content = content)
}
