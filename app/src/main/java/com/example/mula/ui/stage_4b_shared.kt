package com.example.mula.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mula.ui.theme.MulaDimens
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.default_divider_color
import com.example.mula.ui.theme.headline_accent_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.overlay_black_40
import com.example.mula.ui.theme.screen_surface_color
import com.example.mula.ui.theme.text_on_primary

val commerce_screen_padding = PaddingValues(horizontal = 24.dp, vertical = 24.dp)

@Composable
fun CommerceScreenHeader(
    title: String,
    modifier: Modifier = Modifier,
    show_trailing_action: Boolean = false,
    trailing_test_tag: String = "header_action_button",
    trailing_icon: @Composable () -> Unit = {},
    on_back_click: () -> Unit = {},
    on_trailing_click: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxWidth()) {
        IconButton(onClick = on_back_click, modifier = Modifier.align(Alignment.CenterStart).testTag("back_button")) {
            Icon(Icons.Outlined.ArrowBack, contentDescription = "back", tint = body_text_color)
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = body_text_color,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center).testTag("${title}_header")
        )
        if (show_trailing_action) {
            IconButton(
                onClick = on_trailing_click,
                modifier = Modifier.align(Alignment.CenterEnd).testTag(trailing_test_tag)
            ) { trailing_icon() }
        }
    }
}

@Composable
fun CommerceHeaderIcon(icon: String) {
    Text(text = icon, style = MaterialTheme.typography.titleLarge, color = body_text_color)
}

@Composable
fun MethodSummaryCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    test_tag: String = "order_method_summary_card",
    on_change_click: () -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxWidth().testTag(test_tag),
        shape = MulaShapeTokens.large,
        color = screen_surface_color
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(mula_spacing.md.dp),
            horizontalArrangement = Arrangement.spacedBy(mula_spacing.md.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(52.dp).clip(CircleShape).background(headline_accent_color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) { Text("o", color = headline_accent_color) }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium, color = body_text_color, modifier = Modifier.testTag("order_method_name_text"))
                Text(text = description, style = MaterialTheme.typography.bodySmall, color = body_text_color, modifier = Modifier.testTag("order_method_description_text"))
            }
            Text(
                text = "Ganti",
                style = MaterialTheme.typography.labelMedium,
                color = headline_accent_color,
                modifier = Modifier.clickable(onClick = on_change_click).testTag("change_order_method_button")
            )
        }
    }
}

@Composable
fun MenuItemRow(
    name: String,
    description: String,
    price: String,
    modifier: Modifier = Modifier,
    on_click: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth().clickable(onClick = on_click).padding(vertical = mula_spacing.sm.dp).testTag("menu_item_row"),
        horizontalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
    ) {
        ArtworkPlaceholder(label = "Drink", modifier = Modifier.size(92.dp), test_tag = "menu_item_image")
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = name, style = MaterialTheme.typography.titleMedium, color = body_text_color, modifier = Modifier.testTag("menu_item_name_text"))
                IconButton(onClick = {}, modifier = Modifier.size(24.dp).testTag("favorite_toggle_button")) {
                    Icon(Icons.Outlined.FavoriteBorder, contentDescription = "favorite", tint = body_text_color)
                }
            }
            Text(text = description, style = MaterialTheme.typography.bodySmall, color = body_text_color, modifier = Modifier.testTag("menu_item_description_text"))
            Text(text = price, style = MaterialTheme.typography.labelLarge, color = headline_accent_color, modifier = Modifier.testTag("menu_item_price_text"))
        }
    }
}

@Composable
fun BranchListRow(
    area: String,
    address: String,
    distance: String,
    status: String,
    modifier: Modifier = Modifier,
    on_click: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxWidth().clickable(onClick = on_click).testTag("branch_item_row").padding(vertical = mula_spacing.sm.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(area, style = MaterialTheme.typography.titleMedium, color = body_text_color, modifier = Modifier.testTag("branch_area_text"))
                Text(address, style = MaterialTheme.typography.bodySmall, color = body_text_color, modifier = Modifier.testTag("branch_address_text"))
                Text(distance, style = MaterialTheme.typography.bodySmall, color = headline_accent_color, modifier = Modifier.testTag("branch_distance_text"))
                Text(status, style = MaterialTheme.typography.bodySmall, color = body_text_color, modifier = Modifier.testTag("branch_open_status_text"))
            }
            Text(">", style = MaterialTheme.typography.titleMedium, color = body_text_color, modifier = Modifier.testTag("branch_chevron_icon"))
        }
        HorizontalDivider(modifier = Modifier.padding(top = mula_spacing.md.dp), color = default_divider_color)
    }
}

@Composable
fun OrderItemCardRow(
    name: String,
    variant: String,
    price: String,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxWidth(), shape = MulaShapeTokens.medium, color = screen_surface_color) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(mula_spacing.md.dp),
            horizontalArrangement = Arrangement.spacedBy(mula_spacing.md.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(name, style = MaterialTheme.typography.titleMedium, color = body_text_color)
                Text(variant, style = MaterialTheme.typography.bodySmall, color = body_text_color)
                Text(price, style = MaterialTheme.typography.labelMedium, color = headline_accent_color)
            }
            ArtworkPlaceholder(label = "Item", modifier = Modifier.size(64.dp))
        }
    }
}

@Composable
fun DetailSectionTitle(text: String, modifier: Modifier = Modifier) {
    Text(text = text, style = MaterialTheme.typography.labelLarge, color = body_text_color, modifier = modifier)
}

@Composable
fun DetailMetaRow(left: String, right: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(left, style = MaterialTheme.typography.bodyMedium, color = body_text_color)
        Text(right, style = MaterialTheme.typography.bodyMedium, color = body_text_color)
    }
}

@Composable
fun VoucherSectionRow(value: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text("Voucher", style = MaterialTheme.typography.bodyMedium, color = body_text_color)
        Text(value, style = MaterialTheme.typography.bodyMedium, color = body_text_color)
    }
}

@Composable
fun PaymentOptionRow(
    title: String,
    subtitle: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MulaShapeTokens.medium,
        color = screen_surface_color
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(mula_spacing.md.dp),
            horizontalArrangement = Arrangement.spacedBy(mula_spacing.md.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(if (selected) headline_accent_color else screen_surface_color)
                    .border(1.dp, if (selected) headline_accent_color else default_divider_color, CircleShape)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, color = body_text_color)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = body_text_color)
            }
        }
    }
}

@Composable
fun FullHeightScroll(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        content = content
    )
}

@Composable
fun MapPlaceholder(
    label: String,
    modifier: Modifier = Modifier,
    test_tag: String
) {
    Box(modifier = modifier.testTag(test_tag)) {
        ArtworkPlaceholder(label = label, modifier = Modifier.fillMaxSize())
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(24.dp)
                .clip(CircleShape)
                .background(headline_accent_color)
                .testTag("location_pin_marker")
        )
    }
}

@Composable
fun OverlayIconButton(
    modifier: Modifier = Modifier,
    test_tag: String,
    icon: @Composable () -> Unit,
    on_click: () -> Unit = {}
) {
    IconButton(
        onClick = on_click,
        modifier = modifier
            .clip(CircleShape)
            .background(overlay_black_40)
            .testTag(test_tag)
    ) { icon() }
}

@Composable
fun CloseIcon() {
    Icon(Icons.Outlined.Close, contentDescription = "close", tint = body_text_color)
}

@Composable
fun SearchIcon() {
    Icon(Icons.Outlined.Search, contentDescription = "search", tint = body_text_color)
}

@Composable
fun FavoriteIcon() {
    Icon(Icons.Outlined.FavoriteBorder, contentDescription = "favorite", tint = text_on_primary)
}

@Composable
fun BackIconLight() {
    Icon(Icons.Outlined.ArrowBack, contentDescription = "back", tint = text_on_primary)
}

@Composable
fun LocationRow(text: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Outlined.LocationOn, contentDescription = "location", tint = headline_accent_color)
        Text(text, style = MaterialTheme.typography.bodyMedium, color = body_text_color)
    }
}
@Composable
fun SurfaceBlock(
    modifier: Modifier = Modifier,
    tag: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth().then(if (tag != null) Modifier.testTag(tag) else Modifier),
        shape = MulaShapeTokens.large,
        color = screen_surface_color
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(mula_spacing.md.dp),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp),
            content = content
        )
    }
}
