package com.example.mula.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

object MulaShapeTokens {
    val extra_large: CornerBasedShape = RoundedCornerShape(32.dp)
    val large: CornerBasedShape = RoundedCornerShape(24.dp)
    val medium: CornerBasedShape = RoundedCornerShape(18.dp)
    val small: CornerBasedShape = RoundedCornerShape(12.dp)
    val pill: CornerBasedShape = RoundedCornerShape(999.dp)
    val circular = CircleShape
}

val mula_shapes = Shapes(
    small = MulaShapeTokens.small,
    medium = MulaShapeTokens.medium,
    large = MulaShapeTokens.large,
    extraLarge = MulaShapeTokens.extra_large
)
