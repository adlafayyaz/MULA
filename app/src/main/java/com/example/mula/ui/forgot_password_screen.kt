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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.R
import com.example.mula.ui.components.PrimaryButton
import com.example.mula.ui.components.RoundedInputField
import com.example.mula.ui.components.SecondaryTextLink
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.headline_accent_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.ForgotPasswordScreenEvent
import com.example.mula.viewmodel.ForgotPasswordViewModel

data class ForgotPasswordScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun ForgotPasswordScreenRoute(viewModel: ForgotPasswordViewModel = viewModel()) {
    ForgotPasswordScreen(state = viewModel.state) { viewModel.on_event(ForgotPasswordScreenEvent.RetryRequested) }
}

@Composable
fun ForgotPasswordScreen(
    state: ForgotPasswordScreenState,
    onRetry: () -> Unit = {}
) {
    var phone by rememberSaveable { mutableStateOf("") }

    Stage4ABackground(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(stage_4a_screen_padding),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            Text(
                text = "Lupa Kata Sandi",
                style = MaterialTheme.typography.headlineMedium,
                color = headline_accent_color,
                modifier = Modifier.testTag("forgot_password_title_text")
            )
            Text(
                text = "Masukan no. handphone anda untuk mengatur ulang kata sandi",
                style = MaterialTheme.typography.bodyLarge,
                color = body_text_color,
                modifier = Modifier.testTag("forgot_password_description_text")
            )
            ArtworkPlaceholder(
                label = "Reset Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                test_tag = "forgot_password_illustration_image"
            )
            RoundedInputField(
                value = phone,
                on_value_change = { phone = it },
                placeholder_text = "Masukan No. Handphone",
                modifier = Modifier.testTag("forgot_password_phone_input")
            )
            PrimaryButton(
                text = stringResource(R.string.forgot_password_submit),
                on_click = onRetry,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("forgot_password_submit_button")
            )
            SecondaryTextLink(
                text = "Ingat kata sandi? Masuk",
                on_click = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("login_link_button"),
                text_align = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun ForgotPasswordScreenPreview() {
    MulaTheme { ForgotPasswordScreen(state = ForgotPasswordScreenState()) }
}
