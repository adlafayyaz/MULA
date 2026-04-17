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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.components.PrimaryButton
import com.example.mula.ui.components.ProductOptionChip
import com.example.mula.ui.components.QuantityStepper
import com.example.mula.ui.components.RadioOptionRow
import com.example.mula.ui.components.RatingStarRow
import com.example.mula.ui.components.SectionHeaderRow
import com.example.mula.ui.theme.MulaDimens
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.background_app
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.headline_accent_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.screen_surface_color
import com.example.mula.viewmodel.ProductDetailScreenEvent
import com.example.mula.viewmodel.ProductDetailViewModel

data class ProductDetailScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun ProductDetailScreenRoute(
    product_id: String = "creamy_latte",
    viewModel: ProductDetailViewModel = viewModel()
) {
    ProductDetailScreen(state = viewModel.state, product_id = product_id) {
        viewModel.on_event(ProductDetailScreenEvent.RetryRequested)
    }
}

@Composable
fun ProductDetailScreen(
    state: ProductDetailScreenState,
    product_id: String = "creamy_latte",
    onRetry: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize().background(background_app)) {
        ArtworkPlaceholder(
            label = "Creamy Latte",
            modifier = Modifier.fillMaxWidth().height(300.dp).testTag("product_header_image")
        )
        OverlayIconButton(
            modifier = Modifier.align(Alignment.TopStart).padding(24.dp),
            test_tag = "back_button",
            icon = { BackIconLight() }
        )
        OverlayIconButton(
            modifier = Modifier.align(Alignment.TopEnd).padding(24.dp),
            test_tag = "favorite_button",
            icon = { FavoriteIcon() }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 250.dp)
                .background(screen_surface_color, MulaShapeTokens.extra_large),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 28.dp, bottom = MulaDimens.checkout_footer_min_height + 24.dp),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.lg.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp)) {
                    Text("Creamy Latte", style = MaterialTheme.typography.headlineMedium, color = body_text_color, modifier = Modifier.testTag("product_name_text"))
                    Row(horizontalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("5.0", color = body_text_color, modifier = Modifier.testTag("product_rating_text"))
                        RatingStarRow(selected_rating = 5, on_rating_selected = {})
                    }
                    Text(
                        text = "Espresso bold berpadu lembutnya susu dingin berlapis cream yang menggoda.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = body_text_color,
                        modifier = Modifier.testTag("product_description_text")
                    )
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp)) {
                    SectionHeaderRow(title = "Temperatur", trailing_text = "Wajib, Pilih 1")
                    Row(horizontalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp)) {
                        ProductOptionChip(text = "Dingin", is_selected = true, modifier = Modifier.testTag("product_option_chip"), on_click = {})
                        ProductOptionChip(text = "Panas", is_selected = false, on_click = {})
                    }
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp)) {
                    SectionHeaderRow(title = "Ukuran Cup", trailing_text = "Wajib, Pilih 1")
                    RadioOptionRow(title = "Mungil", is_selected = true, modifier = Modifier.testTag("radio_option_row"), on_click = {})
                    RadioOptionRow(title = "Sedang", trailing_text = "+2000", is_selected = false, on_click = {})
                    RadioOptionRow(title = "Badag", trailing_text = "+5000", is_selected = false, on_click = {})
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp)) {
                    SectionHeaderRow(title = "Topping Gratis", trailing_text = "Opsional, Pilih 1")
                    RadioOptionRow(title = "Saus Karamel", is_selected = true, on_click = {})
                    RadioOptionRow(title = "Bubuk Cokelat", is_selected = false, on_click = {})
                    RadioOptionRow(title = "Whip Cream", is_selected = false, on_click = {})
                }
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(screen_surface_color)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(mula_spacing.md.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            QuantityStepper(
                quantity = 1,
                on_increment = {},
                on_decrement = {},
                modifier = Modifier.testTag("quantity_stepper")
            )
            PrimaryButton(
                text = "Beli Rp. 22.000",
                on_click = onRetry,
                modifier = Modifier.weight(1f).testTag("buy_button")
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun ProductDetailScreenPreview() {
    MulaTheme { ProductDetailScreen(state = ProductDetailScreenState()) }
}
