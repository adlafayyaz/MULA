package com.example.mula.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.components.BranchSelectorCard
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.background_app
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.CatalogScreenEvent
import com.example.mula.viewmodel.CatalogViewModel

data class CatalogScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun CatalogScreenRoute(
    order_method: String = "delivery",
    on_back: () -> Unit = {},
    on_branch: () -> Unit = {},
    on_product: () -> Unit = {},
    viewModel: CatalogViewModel = viewModel()
) {
    CatalogScreen(
        state = viewModel.state,
        order_method = if (order_method.isBlank()) "delivery" else order_method,
        on_back = on_back,
        on_branch = on_branch,
        on_product = on_product
    ) { viewModel.on_event(CatalogScreenEvent.RetryRequested) }
}

@Composable
fun CatalogScreen(
    state: CatalogScreenState,
    order_method: String = "delivery",
    on_back: () -> Unit = {},
    on_branch: () -> Unit = {},
    on_product: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    val title = if (order_method == "pickup") "Ambil Sendiri" else "Pesan Antar"
    val categories = listOf(
        "Menu Terbaik" to listOf(
            Triple("Creamy Latte", "Espresso bold berpadu susu lembut.", "Rp. 22.000"),
            Triple("Matcha Depanmu", "Matcha creamy dengan sentuhan manis.", "Rp. 24.000")
        ),
        "MULA Signature" to listOf(
            Triple("Cappucino Assassino", "Kopi creamy dengan foam halus.", "Rp. 23.000")
        ),
        "Americano Series" to listOf(
            Triple("MULAmericano", "Americano segar untuk hari panjang.", "Rp. 20.000")
        )
    )

    androidx.compose.foundation.layout.Box(modifier = Modifier.fillMaxSize().background(background_app)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            item {
                CommerceScreenHeader(
                    title = title,
                    modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp).testTag("catalog_header_container"),
                    show_trailing_action = true,
                    trailing_test_tag = "catalog_search_button",
                    trailing_icon = { SearchIcon() },
                    on_back_click = on_back
                )
            }
            item {
                BranchSelectorCard(
                    branch_name = "MULA Cirendeu",
                    branch_distance_text = "1,2 km",
                    modifier = Modifier.padding(horizontal = 24.dp).testTag("selected_branch_card"),
                    on_click = on_branch
                )
            }
            categories.forEach { category ->
                item {
                    androidx.compose.material3.Text(
                        text = category.first,
                        style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                        color = body_text_color,
                        modifier = Modifier.padding(horizontal = 24.dp).testTag("category_title_text")
                    )
                }
                items(category.second) { item ->
                    MenuItemRow(
                        name = item.first,
                        description = item.second,
                        price = item.third,
                        modifier = Modifier.padding(horizontal = 24.dp),
                        on_click = on_product
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun CatalogScreenPreview() {
    MulaTheme { CatalogScreen(state = CatalogScreenState()) }
}
