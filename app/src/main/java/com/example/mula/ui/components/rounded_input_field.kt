package com.example.mula.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.mula.ui.theme.MulaDimens
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.default_divider_color
import com.example.mula.ui.theme.headline_accent_color
import com.example.mula.ui.theme.muted_text_color
import com.example.mula.ui.theme.primary_button_background_color
import com.example.mula.ui.theme.screen_surface_color

@Composable
fun RoundedInputField(
    value: String,
    on_value_change: (String) -> Unit,
    placeholder_text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    read_only: Boolean = false,
    single_line: Boolean = true,
    error_message: String? = null,
    leading_icon: ImageVector? = null,
    trailing_icon: ImageVector? = null,
    on_trailing_icon_click: (() -> Unit)? = null,
    visual_transformation: VisualTransformation = VisualTransformation.None
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = on_value_change,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(MulaDimens.text_field_height),
            enabled = enabled,
            readOnly = read_only,
            placeholder = { Text(text = placeholder_text, style = MaterialTheme.typography.bodyMedium) },
            leadingIcon = if (leading_icon != null) ({ androidx.compose.material3.Icon(leading_icon, null) }) else null,
            trailingIcon = if (trailing_icon != null) ({
                androidx.compose.material3.IconButton(
                    onClick = { on_trailing_icon_click?.invoke() },
                    enabled = enabled && on_trailing_icon_click != null
                ) {
                    androidx.compose.material3.Icon(trailing_icon, null)
                }
            }) else null,
            shape = MulaShapeTokens.medium,
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (error_message == null) primary_button_background_color else headline_accent_color,
                unfocusedBorderColor = if (error_message == null) default_divider_color else headline_accent_color,
                disabledBorderColor = default_divider_color,
                focusedTextColor = body_text_color,
                unfocusedTextColor = body_text_color,
                disabledTextColor = muted_text_color,
                focusedPlaceholderColor = body_text_color,
                unfocusedPlaceholderColor = body_text_color,
                disabledPlaceholderColor = muted_text_color,
                focusedContainerColor = screen_surface_color,
                unfocusedContainerColor = screen_surface_color,
                disabledContainerColor = screen_surface_color
            ),
            singleLine = single_line,
            keyboardOptions = KeyboardOptions.Default,
            visualTransformation = visual_transformation
        )
        if (error_message != null) {
            Text(
                text = error_message,
                style = MaterialTheme.typography.bodySmall,
                color = headline_accent_color
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun RoundedInputFieldPreview() {
    MulaTheme { RoundedInputField(value = "", on_value_change = {}, placeholder_text = "username") }
}
