package com.example.mula.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mula.ui.theme.MulaDimens
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.active_tab_text_color
import com.example.mula.ui.theme.brand_brown_primary
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.screen_surface_color
import com.example.mula.ui.theme.text_on_primary

const val home_tab_button = "home_tab_button"
const val voucher_tab_button = "voucher_tab_button"
const val order_history_tab_button = "order_history_tab_button"
const val profile_tab_button = "profile_tab_button"

private data class TabItem(val id: String, val label: String)

@Composable
fun CustomBottomTabBar(
    selected_tab: String,
    on_tab_selected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        TabItem(home_tab_button, "Home"),
        TabItem(voucher_tab_button, "Voucher"),
        TabItem(order_history_tab_button, "Pesanan"),
        TabItem(profile_tab_button, "Profil")
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(MulaDimens.bottom_tab_bar_height)
            .background(
                color = brand_brown_primary,
                shape = MulaShapeTokens.extra_large
            )
            .padding(horizontal = mula_spacing.sm.dp, vertical = mula_spacing.xs.dp),
        horizontalArrangement = Arrangement.spacedBy(mula_spacing.xs.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val is_selected = item.id == selected_tab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = if (is_selected) screen_surface_color else androidx.compose.ui.graphics.Color.Transparent,
                        shape = MulaShapeTokens.pill
                    )
                    .clickable { on_tab_selected(item.id) }
                    .padding(vertical = mula_spacing.sm.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (is_selected) active_tab_text_color else text_on_primary
                    )
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (is_selected) active_tab_text_color else text_on_primary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun CustomBottomTabBarPreview() {
    MulaTheme { CustomBottomTabBar(selected_tab = home_tab_button, on_tab_selected = {}) }
}
