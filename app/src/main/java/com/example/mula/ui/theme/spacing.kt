package com.example.mula.ui.theme

import androidx.compose.runtime.Immutable

@Immutable
data class Spacing(
    val xxs: Int = 4,
    val xs: Int = 8,
    val sm: Int = 12,
    val md: Int = 16,
    val lg: Int = 20,
    val xl: Int = 24,
    val xxl: Int = 32,
    val xxxl: Int = 40
)

val mula_spacing = Spacing()
