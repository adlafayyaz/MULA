package com.example.mula.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.components.TokenBalanceCard
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.RewardsScreenEvent
import com.example.mula.viewmodel.RewardsViewModel

data class RewardsScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun RewardsScreenRoute(viewModel: RewardsViewModel = viewModel()) {
    RewardsScreen(state = viewModel.state) { viewModel.on_event(RewardsScreenEvent.RetryRequested) }
}

@Composable
fun RewardsScreen(
    state: RewardsScreenState,
    onRetry: () -> Unit = {}
) {
    val rewards = listOf(
        Triple("Americano Gratis", "30 MULA Token", "Kurang 10 token"),
        Triple("Latte Gratis", "40 MULA Token", "Kurang 5 token"),
        Triple("Diskon 25%", "25 MULA Token", "Bisa ditukar"),
        Triple("Croissant Gratis", "20 MULA Token", "Bisa ditukar")
    )
    val filters = listOf("Semua", "Minuman", "Makanan")
    var selected_filter by rememberSaveable { mutableStateOf(filters.first()) }

    Stage4ABackground(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(stage_4a_screen_padding)) {
            ScreenHeaderRow(title = "MULA Rewards")
            TokenBalanceCard(
                token_text = "30 MULA Token",
                button_text = "Tukarkan",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = mula_spacing.md.dp)
                    .testTag("rewards_token_balance_card"),
                on_button_click = onRetry
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = mula_spacing.md.dp)
                    .horizontalScroll(rememberScrollState())
                    .testTag("reward_filter_row"),
                horizontalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp)
            ) {
                filters.forEach { filter ->
                    RewardFilterChipItem(
                        text = filter,
                        selected = filter == selected_filter,
                        on_click = { selected_filter = filter }
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = mula_spacing.md.dp)
                    .testTag("reward_list"),
                verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
            ) {
                items(rewards.chunked(2)) { row_items ->
                    Row(horizontalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)) {
                        row_items.forEachIndexed { index, reward ->
                            RewardItemCard(
                                name = reward.first,
                                token_text = reward.second,
                                shortage_text = reward.third,
                                modifier = Modifier.weight(1f),
                                test_tag = "reward_item_card_${reward.first}_$index"
                            )
                        }
                        if (row_items.size == 1) {
                            androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun RewardsScreenPreview() {
    MulaTheme { RewardsScreen(state = RewardsScreenState()) }
}
