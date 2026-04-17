package com.example.mula.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mula.ui.theme.MulaDimens
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.app_background_color
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.brand_brown_primary
import com.example.mula.ui.theme.default_divider_color
import com.example.mula.ui.theme.headline_accent_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.muted_text_color
import com.example.mula.ui.theme.overlay_black_40
import com.example.mula.ui.theme.screen_surface_color
import com.example.mula.ui.theme.text_on_primary

val stage_4a_screen_padding = PaddingValues(horizontal = MulaDimens.screen_horizontal_padding, vertical = 24.dp)

@Composable
fun Stage4ABackground(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = modifier
            .background(app_background_color)
            .fillMaxWidth(),
        content = content
    )
}

@Composable
fun ArtworkPlaceholder(
    label: String,
    modifier: Modifier = Modifier,
    test_tag: String? = null
) {
    Box(
        modifier = modifier
            .then(if (test_tag != null) Modifier.testTag(test_tag) else Modifier)
            .clip(MulaShapeTokens.extra_large)
            .background(
                Brush.linearGradient(
                    listOf(
                        headline_accent_color.copy(alpha = 0.18f),
                        brand_brown_primary.copy(alpha = 0.10f),
                        screen_surface_color
                    )
                )
            )
            .border(1.dp, default_divider_color, MulaShapeTokens.extra_large),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = headline_accent_color,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(mula_spacing.lg.dp)
        )
    }
}

@Composable
fun PagerIndicator(
    page_count: Int,
    selected_index: Int,
    modifier: Modifier = Modifier,
    test_tag: String? = null
) {
    Row(
        modifier = modifier.then(if (test_tag != null) Modifier.testTag(test_tag) else Modifier),
        horizontalArrangement = Arrangement.spacedBy(mula_spacing.xs.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(page_count) { index ->
            Box(
                modifier = Modifier
                    .size(if (index == selected_index) 24.dp else 8.dp, 8.dp)
                    .clip(CircleShape)
                    .background(if (index == selected_index) brand_brown_primary else default_divider_color)
            )
        }
    }
}

@Composable
fun ScreenHeaderRow(
    title: String,
    modifier: Modifier = Modifier,
    show_back_button: Boolean = true,
    test_tag: String? = null,
    on_back_click: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(if (test_tag != null) Modifier.testTag(test_tag) else Modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (show_back_button) {
            IconButton(onClick = on_back_click, modifier = Modifier.testTag("back_button")) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "back", tint = body_text_color)
            }
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = body_text_color,
            modifier = Modifier.weight(1f),
            textAlign = if (show_back_button) TextAlign.Center else TextAlign.Start
        )
        Spacer(modifier = Modifier.width(48.dp))
    }
}

@Composable
fun ServiceModeCard(
    text: String,
    modifier: Modifier = Modifier,
    test_tag: String? = null,
    on_click: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .then(if (test_tag != null) Modifier.testTag(test_tag) else Modifier)
            .clickable(onClick = on_click),
        shape = MulaShapeTokens.large,
        color = screen_surface_color
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mula_spacing.lg.dp),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(headline_accent_color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "o", color = headline_accent_color, style = MaterialTheme.typography.titleLarge)
            }
            Text(text = text, style = MaterialTheme.typography.titleMedium, color = body_text_color, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun RewardFilterChipItem(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    on_click: () -> Unit = {}
) {
    FilterChip(
        selected = selected,
        onClick = on_click,
        label = { Text(text) },
        modifier = modifier,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = brand_brown_primary,
            selectedLabelColor = text_on_primary,
            containerColor = screen_surface_color,
            labelColor = body_text_color
        )
    )
}

@Composable
fun RewardItemCard(
    name: String,
    token_text: String,
    shortage_text: String,
    modifier: Modifier = Modifier,
    test_tag: String? = null
) {
    Surface(
        modifier = modifier.then(if (test_tag != null) Modifier.testTag(test_tag) else Modifier),
        shape = MulaShapeTokens.large,
        color = screen_surface_color
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mula_spacing.md.dp),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp)
        ) {
            ArtworkPlaceholder(label = "Reward", modifier = Modifier.fillMaxWidth().height(112.dp))
            Text(text = name, style = MaterialTheme.typography.titleMedium, color = body_text_color)
            Text(text = token_text, style = MaterialTheme.typography.labelMedium, color = headline_accent_color)
            Text(text = shortage_text, style = MaterialTheme.typography.bodySmall, color = muted_text_color)
        }
    }
}

@Composable
fun NotificationCardBlock(
    meta_text: String,
    title: String,
    body: String,
    footer: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MulaShapeTokens.large,
        color = screen_surface_color
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mula_spacing.lg.dp),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp)
        ) {
            Text(text = meta_text, style = MaterialTheme.typography.bodySmall, color = muted_text_color)
            Text(text = title, style = MaterialTheme.typography.titleMedium, color = body_text_color)
            Text(text = body, style = MaterialTheme.typography.bodyMedium, color = body_text_color)
            Text(text = footer, style = MaterialTheme.typography.bodySmall, color = muted_text_color)
        }
    }
}

@Composable
fun ProfileFieldRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    show_action: Boolean = false,
    test_tag: String? = null,
    on_click: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(if (test_tag != null) Modifier.testTag(test_tag) else Modifier)
            .clickable(enabled = show_action, onClick = on_click)
            .padding(vertical = mula_spacing.sm.dp),
        verticalArrangement = Arrangement.spacedBy(mula_spacing.xxs.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = label, style = MaterialTheme.typography.bodySmall, color = muted_text_color)
                Text(text = value, style = MaterialTheme.typography.bodyLarge, color = body_text_color)
            }
            if (show_action) {
                Icon(Icons.Outlined.Edit, contentDescription = "edit", tint = body_text_color)
            }
        }
        HorizontalDivider(color = default_divider_color)
    }
}

@Composable
fun OtpDigitBox(
    value: String,
    modifier: Modifier = Modifier,
    test_tag: String
) {
    Box(
        modifier = modifier
            .size(60.dp)
            .clip(MulaShapeTokens.medium)
            .background(screen_surface_color)
            .border(1.dp, default_divider_color, MulaShapeTokens.medium)
            .testTag(test_tag),
        contentAlignment = Alignment.Center
    ) {
        Text(text = value, style = MaterialTheme.typography.headlineMedium, color = body_text_color)
    }
}

@Composable
fun NotificationActionButton(modifier: Modifier = Modifier, on_click: () -> Unit = {}) {
    IconButton(onClick = on_click, modifier = modifier.testTag("notification_button")) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(overlay_black_40)
                .padding(10.dp)
        ) {
            Icon(Icons.Outlined.NotificationsNone, contentDescription = "notifications", tint = text_on_primary)
        }
    }
}
