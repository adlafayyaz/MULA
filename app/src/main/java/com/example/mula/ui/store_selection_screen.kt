package com.example.mula.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.StoreSelectionScreenEvent
import com.example.mula.viewmodel.StoreSelectionViewModel

data class StoreSelectionScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun StoreSelectionScreenRoute(
    order_method: String = "delivery",
    on_back: () -> Unit = {},
    on_change_method: () -> Unit = {},
    on_branch_selected: (String) -> Unit = {},
    viewModel: StoreSelectionViewModel = viewModel()
) {
    StoreSelectionScreen(
        state = viewModel.state,
        order_method = if (order_method.isBlank()) "delivery" else order_method,
        on_back = on_back,
        on_change_method = on_change_method,
        on_branch_selected = on_branch_selected
    ) { viewModel.on_event(StoreSelectionScreenEvent.RetryRequested) }
}

@Composable
fun StoreSelectionScreen(
    state: StoreSelectionScreenState,
    order_method: String = "delivery",
    on_back: () -> Unit = {},
    on_change_method: () -> Unit = {},
    on_branch_selected: (String) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    val method_name = if (order_method == "pickup") "Ambil Sendiri" else "Pesan Antar"
    val method_desc = if (order_method == "pickup") "Ambil ke toko tanpa antri" else "Segera diantar ke lokasimu"
    val branches = listOf(
        listOf("Cirendeu", "Jl. Cirendeu Raya No. 12", "1,2 km dari lokasimu", "Buka 09.00 - 22.00"),
        listOf("Pamulang", "Jl. Pamulang Permai Blok A", "3,9 km dari lokasimu", "Buka 09.00 - 22.00"),
        listOf("Lebak Bulus", "Jl. Karang Tengah Raya", "4,2 km dari lokasimu", "Tutup 10.00 - 21.00"),
        listOf("Pondok Labu", "Jl. RS Fatmawati Raya", "7 km dari lokasimu", "Buka 09.00 - 22.00")
    )

    Stage4ABackground(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            item { CommerceScreenHeader(title = "MULA Store", on_back_click = on_back) }
            item {
                MethodSummaryCard(
                    title = method_name,
                    description = method_desc,
                    on_change_click = on_change_method
                )
            }
            item {
                Text(
                    text = "Gerai Cabang",
                    style = MaterialTheme.typography.titleMedium,
                    color = body_text_color,
                    modifier = Modifier.testTag("branch_section_title_text")
                )
            }
            items(branches) { branch ->
                BranchListRow(
                    area = branch[0],
                    address = branch[1],
                    distance = branch[2],
                    status = branch[3],
                    modifier = Modifier.testTag("branch_list"),
                    on_click = { on_branch_selected(branch[0]) }
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun StoreSelectionScreenPreview() {
    MulaTheme { StoreSelectionScreen(state = StoreSelectionScreenState()) }
}
