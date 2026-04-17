package com.example.mula.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val mula_color_scheme = lightColorScheme(
    primary = brand_brown_primary,
    onPrimary = text_on_primary,
    background = background_app,
    onBackground = text_primary,
    surface = surface_primary,
    onSurface = text_primary,
    outline = border_default
)

@Composable
fun MulaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = mula_color_scheme,
        typography = mula_typography,
        shapes = mula_shapes,
        content = content
    )
}
