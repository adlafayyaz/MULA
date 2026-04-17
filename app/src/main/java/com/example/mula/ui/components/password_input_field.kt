package com.example.mula.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.mula.ui.theme.MulaTheme

@Composable
fun PasswordInputField(
    value: String,
    on_value_change: (String) -> Unit,
    placeholder_text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    is_password_visible: Boolean,
    on_visibility_toggle: () -> Unit,
    error_message: String? = null
) {
    RoundedInputField(
        value = value,
        on_value_change = on_value_change,
        placeholder_text = placeholder_text,
        modifier = modifier,
        enabled = enabled,
        error_message = error_message,
        trailing_icon = if (is_password_visible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
        on_trailing_icon_click = on_visibility_toggle,
        visual_transformation = if (is_password_visible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun PasswordInputFieldPreview() {
    MulaTheme {
        PasswordInputField(
            value = "secret123",
            on_value_change = {},
            placeholder_text = "kata sandi",
            is_password_visible = false,
            on_visibility_toggle = {}
        )
    }
}
