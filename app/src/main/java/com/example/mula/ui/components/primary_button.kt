package com.example.mula.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mula.ui.theme.MulaDimens
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.default_divider_color
import com.example.mula.ui.theme.muted_text_color
import com.example.mula.ui.theme.primary_button_background_color
import com.example.mula.ui.theme.primary_button_text_color

@Composable
fun PrimaryButton(
    text: String,
    on_click: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    is_loading: Boolean = false
) {
    Button(
        onClick = on_click,
        modifier = modifier
            .fillMaxWidth()
            .height(MulaDimens.primary_button_height),
        enabled = enabled && !is_loading,
        shape = MulaShapeTokens.pill,
        colors = ButtonDefaults.buttonColors(
            containerColor = primary_button_background_color,
            contentColor = primary_button_text_color,
            disabledContainerColor = default_divider_color,
            disabledContentColor = muted_text_color
        )
    ) {
        if (is_loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.dp,
                color = primary_button_text_color
            )
        } else {
            Text(text = text, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun PrimaryButtonPreview() {
    MulaTheme { PrimaryButton(text = "masuk", on_click = {}) }
}
