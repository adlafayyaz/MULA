package com.example.mula.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.default_divider_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.screen_surface_color

@Composable
fun BranchSelectorCard(
    branch_name: String,
    branch_distance_text: String,
    modifier: Modifier = Modifier,
    on_click: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(screen_surface_color, MulaShapeTokens.large)
            .clickable(onClick = on_click)
            .padding(mula_spacing.md.dp),
        horizontalArrangement = Arrangement.spacedBy(mula_spacing.md.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "⌂", style = MaterialTheme.typography.titleLarge, color = body_text_color)
        Box(modifier = Modifier.width(1.dp).height(36.dp).background(default_divider_color))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = branch_name, style = MaterialTheme.typography.titleMedium, color = body_text_color)
            Text(text = branch_distance_text, style = MaterialTheme.typography.bodySmall, color = body_text_color)
        }
        Text(text = ">", style = MaterialTheme.typography.titleMedium, color = body_text_color)
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun BranchSelectorCardPreview() {
    MulaTheme { BranchSelectorCard(branch_name = "Mula Central", branch_distance_text = "1.2 km", on_click = {}) }
}
