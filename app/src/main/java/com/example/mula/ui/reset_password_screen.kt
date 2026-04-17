package com.example.mula.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.R
import com.example.mula.ui.components.PasswordInputField
import com.example.mula.ui.components.PrimaryButton
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.headline_accent_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.ResetPasswordScreenEvent
import com.example.mula.viewmodel.ResetPasswordViewModel

data class ResetPasswordScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun ResetPasswordScreenRoute(viewModel: ResetPasswordViewModel = viewModel()) {
    ResetPasswordScreen(state = viewModel.state) { viewModel.on_event(ResetPasswordScreenEvent.RetryRequested) }
}

@Composable
fun ResetPasswordScreen(
    state: ResetPasswordScreenState,
    onRetry: () -> Unit = {}
) {
    var password by rememberSaveable { mutableStateOf("") }
    var confirm_password by rememberSaveable { mutableStateOf("") }
    var password_visible by rememberSaveable { mutableStateOf(false) }
    var confirm_visible by rememberSaveable { mutableStateOf(false) }

    Stage4ABackground(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(stage_4a_screen_padding),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            Text(
                text = "Atur Ulang Kata Sandi",
                style = MaterialTheme.typography.headlineMedium,
                color = headline_accent_color,
                modifier = Modifier.testTag("reset_password_title_text")
            )
            Text(
                text = "Jangan sampai lupa lagi ya!",
                style = MaterialTheme.typography.bodyLarge,
                color = body_text_color,
                modifier = Modifier.testTag("reset_password_description_text")
            )
            ArtworkPlaceholder(
                label = "Password Baru",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                test_tag = "reset_password_illustration_image"
            )
            PasswordInputField(
                value = password,
                on_value_change = { password = it },
                placeholder_text = "Kata sandi baru",
                is_password_visible = password_visible,
                on_visibility_toggle = { password_visible = !password_visible },
                modifier = Modifier.testTag("new_password_input")
            )
            PasswordInputField(
                value = confirm_password,
                on_value_change = { confirm_password = it },
                placeholder_text = "Konfirmasi kata sandi",
                is_password_visible = confirm_visible,
                on_visibility_toggle = { confirm_visible = !confirm_visible },
                modifier = Modifier.testTag("confirm_new_password_input")
            )
            PrimaryButton(
                text = stringResource(R.string.reset_password_submit),
                on_click = onRetry,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("save_new_password_button")
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun ResetPasswordScreenPreview() {
    MulaTheme { ResetPasswordScreen(state = ResetPasswordScreenState()) }
}
