package com.example.mula.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.components.CustomBottomTabBar
import com.example.mula.ui.components.SecondaryTextLink
import com.example.mula.ui.components.profile_tab_button
import com.example.mula.ui.theme.MulaDimens
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.background_app
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.headline_accent_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.screen_surface_color
import com.example.mula.viewmodel.ProfileScreenEvent
import com.example.mula.viewmodel.ProfileViewModel

data class ProfileScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun ProfileScreenRoute(
    on_logout: () -> Unit = {},
    on_tab_selected: (String) -> Unit = {},
    viewModel: ProfileViewModel = viewModel()
) {
    ProfileScreen(
        state = ProfileScreenState(
            is_loading = viewModel.state.is_loading,
            error_message = viewModel.state.error_message
        ),
        on_logout = on_logout,
        on_tab_selected = on_tab_selected
    ) {
        viewModel.on_event(ProfileScreenEvent.OnRetryClicked)
    }
}

@Composable
fun ProfileScreen(
    state: ProfileScreenState,
    on_logout: () -> Unit = {},
    on_tab_selected: (String) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background_app)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp,
                bottom = MulaDimens.bottom_tab_bar_height + 32.dp
            ),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            item {
                Text(
                    text = "Akun Saya",
                    style = MaterialTheme.typography.headlineMedium,
                    color = body_text_color,
                    modifier = Modifier.testTag("profile_title_text")
                )
            }
            item {
                Text(
                    text = "Bergabung pada: 11 Juni 2025",
                    style = MaterialTheme.typography.bodyMedium,
                    color = headline_accent_color,
                    modifier = Modifier.testTag("join_date_text")
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("profile_avatar_container"),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(MulaShapeTokens.extra_large)
                            .background(screen_surface_color)
                            .testTag("profile_avatar_image"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("BJ", style = MaterialTheme.typography.headlineMedium, color = headline_accent_color)
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(start = 78.dp)
                            .background(headline_accent_color, MulaShapeTokens.pill)
                            .testTag("edit_avatar_button")
                    ) {
                        Icon(Icons.Outlined.Edit, contentDescription = "edit avatar", tint = screen_surface_color)
                    }
                }
            }
            item { ProfileFieldRow("Nama Pengguna", "Bonhgoed Jaya", test_tag = "profile_username_row", show_action = true) }
            item { ProfileFieldRow("No. Handphone", "+62 813-7783-6098", test_tag = "profile_phone_row", show_action = true) }
            item { ProfileFieldRow("Tanggal Lahir", "25/06/2000", test_tag = "profile_birth_date_row") }
            item { ProfileFieldRow("Jenis Kelamin", "Laki-laki", test_tag = "profile_gender_row") }
            item {
                SecondaryTextLink(
                    text = "Keluar",
                    on_click = on_logout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("logout_button"),
                    text_align = androidx.compose.ui.text.style.TextAlign.Start
                )
            }
        }

        CustomBottomTabBar(
            selected_tab = profile_tab_button,
            on_tab_selected = on_tab_selected,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .testTag("main_bottom_tab_bar")
        )
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun ProfileScreenPreview() {
    MulaTheme { ProfileScreen(state = ProfileScreenState()) }
}
