package com.example.mula.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.default_divider_color
import com.example.mula.ui.theme.primary_button_background_color

@Composable
fun RatingStarRow(
    selected_rating: Int,
    modifier: Modifier = Modifier,
    on_rating_selected: (Int) -> Unit,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(5) { index ->
            Text(
                text = "*",
                modifier = Modifier.clickable(enabled = enabled) { on_rating_selected(index + 1) },
                style = MaterialTheme.typography.headlineMedium,
                color = if (index < selected_rating) primary_button_background_color else default_divider_color
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingStarRowPreview() {
    MulaTheme { RatingStarRow(selected_rating = 4, on_rating_selected = {}) }
}
