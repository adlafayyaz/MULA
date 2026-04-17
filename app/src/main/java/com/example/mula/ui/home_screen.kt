package com.example.mula.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.components.CustomBottomTabBar
import com.example.mula.ui.components.PromoCard
import com.example.mula.ui.components.SectionHeaderRow
import com.example.mula.ui.components.TokenBalanceCard
import com.example.mula.ui.components.home_tab_button
import com.example.mula.ui.theme.MulaDimens
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.headline_accent_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.screen_surface_color
import com.example.mula.ui.theme.text_on_primary
import com.example.mula.viewmodel.HomeScreenEvent
import com.example.mula.viewmodel.HomeViewModel

data class HomeScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun HomeScreenRoute(
    on_notification: () -> Unit = {},
    on_rewards: () -> Unit = {},
    on_delivery: () -> Unit = {},
    on_pickup: () -> Unit = {},
    on_tab_selected: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel()
) {
    HomeScreen(
        state = HomeScreenState(
            is_loading = viewModel.state.is_loading,
            error_message = viewModel.state.error_message
        ),
        on_notification = on_notification,
        on_rewards = on_rewards,
        on_delivery = on_delivery,
        on_pickup = on_pickup,
        on_tab_selected = on_tab_selected
    ) { viewModel.on_event(HomeScreenEvent.OnRetryClicked) }
}

@Composable
fun HomeScreen(
    state: HomeScreenState,
    on_notification: () -> Unit = {},
    on_rewards: () -> Unit = {},
    on_delivery: () -> Unit = {},
    on_pickup: () -> Unit = {},
    on_tab_selected: (String) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    val promos = listOf(
        "Beli 1 Gratis 1" to "Beli apapun dengan pembelian minimum 35k dan dapatkan 1 minuman gratis",
        "Potongan 40%" to "Pembayaran menggunakan GoPay dan dapatkan potongan 40% di setiap pembelian"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(com.example.mula.ui.theme.background_app)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = MulaDimens.bottom_tab_bar_height + 36.dp),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .testTag("home_hero_banner_container")
                ) {
                    ArtworkPlaceholder(
                        label = "Morning Coffee Banner",
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("home_banner_pager")
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(horizontal = 24.dp, vertical = 28.dp)
                    ) {
                        Text(
                            text = "Selamat pagi,",
                            style = MaterialTheme.typography.bodyLarge,
                            color = text_on_primary,
                            modifier = Modifier.testTag("greeting_label_text")
                        )
                        Text(
                            text = "Bonhgoed",
                            style = MaterialTheme.typography.headlineMedium,
                            color = text_on_primary,
                            modifier = Modifier.testTag("user_name_text")
                        )
                    }
                    NotificationActionButton(modifier = Modifier.align(Alignment.TopEnd).padding(16.dp), on_click = on_notification)
                }
            }
            item {
                TokenBalanceCard(
                    token_text = "30 MULA Token",
                    button_text = "Tukarkan",
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .testTag("token_balance_card"),
                    on_button_click = on_rewards
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .testTag("service_mode_row"),
                    horizontalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
                ) {
                    ServiceModeCard(
                        text = "Pesan antar",
                        modifier = Modifier.weight(1f),
                        test_tag = "delivery_mode_card",
                        on_click = on_delivery
                    )
                    ServiceModeCard(
                        text = "Ambil Sendiri",
                        modifier = Modifier.weight(1f),
                        test_tag = "pickup_mode_card",
                        on_click = on_pickup
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .background(screen_surface_color, MulaShapeTokens.extra_large)
                        .padding(20.dp)
                        .testTag("promo_section_container"),
                    verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
                ) {
                    SectionHeaderRow(
                        title = "Promo Hari ini",
                        trailing_text = "(2 Promo)",
                        modifier = Modifier.testTag("promo_section_header")
                    )
                    promos.forEachIndexed { index, promo ->
                        PromoCard(
                            title = promo.first,
                            description = promo.second,
                            image_res_name = "Promo",
                            modifier = Modifier.testTag(
                                if (index == 0) "promo_card_buy_one_get_one" else "promo_card_discount_gopay"
                            ),
                            on_click = {}
                        )
                    }
                }
            }
        }

        CustomBottomTabBar(
            selected_tab = home_tab_button,
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
private fun HomeScreenPreview() {
    MulaTheme { HomeScreen(state = HomeScreenState()) }
}
