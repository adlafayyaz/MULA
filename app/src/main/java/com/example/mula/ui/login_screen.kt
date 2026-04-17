package com.example.mula.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.components.PasswordInputField
import com.example.mula.ui.components.PrimaryButton
import com.example.mula.ui.components.RoundedInputField
import com.example.mula.ui.components.SecondaryTextLink
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.LoginScreenEvent
import com.example.mula.viewmodel.LoginViewModel

data class LoginScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun LoginScreenRoute(
    on_login_success: () -> Unit = {},
    on_forgot_password: () -> Unit = {},
    on_register: () -> Unit = {},
    viewModel: LoginViewModel = viewModel()
) {
    LoginScreen(
        state = LoginScreenState(
            is_loading = viewModel.state.is_loading,
            error_message = viewModel.state.error_message
        ),
        on_login_success = on_login_success,
        on_forgot_password = on_forgot_password,
        on_register = on_register
    ) { viewModel.on_event(LoginScreenEvent.OnErrorMessageDismissed) }
}

@Composable
fun LoginScreen(
    state: LoginScreenState,
    on_login_success: () -> Unit = {},
    on_forgot_password: () -> Unit = {},
    on_register: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var password_visible by rememberSaveable { mutableStateOf(false) }

    Stage4ABackground(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(stage_4a_screen_padding),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            ArtworkPlaceholder(
                label = "Login",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                test_tag = "login_illustration_image"
            )
            RoundedInputField(
                value = username,
                on_value_change = { username = it },
                placeholder_text = "Masukan nama pengguna",
                modifier = Modifier.testTag("username_input")
            )
            PasswordInputField(
                value = password,
                on_value_change = { password = it },
                placeholder_text = "Masukan kata sandi",
                is_password_visible = password_visible,
                on_visibility_toggle = { password_visible = !password_visible },
                modifier = Modifier.testTag("password_input")
            )
            SecondaryTextLink(
                text = "Lupa kata sandi",
                on_click = on_forgot_password,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("forgot_password_button"),
                text_align = TextAlign.End
            )
            PrimaryButton(
                text = "Masuk",
                on_click = on_login_success,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("login_button")
            )
            SecondaryTextLink(
                text = "Belum punya akun? Daftar",
                on_click = on_register,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .testTag("register_link_button"),
                text_align = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun LoginScreenPreview() {
    MulaTheme { LoginScreen(state = LoginScreenState()) }
}
