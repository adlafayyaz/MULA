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
import com.example.mula.viewmodel.RegisterScreenEvent
import com.example.mula.viewmodel.RegisterViewModel

data class RegisterScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun RegisterScreenRoute(
    on_login: () -> Unit = {},
    on_register_success: () -> Unit = {},
    viewModel: RegisterViewModel = viewModel()
) {
    RegisterScreen(
        state = RegisterScreenState(
            is_loading = viewModel.state.is_loading,
            error_message = viewModel.state.error_message
        ),
        on_login = on_login,
        on_register_success = on_register_success
    ) { viewModel.on_event(RegisterScreenEvent.OnErrorMessageDismissed) }
}

@Composable
fun RegisterScreen(
    state: RegisterScreenState,
    on_login: () -> Unit = {},
    on_register_success: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    var username by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("") }
    var birth_date by rememberSaveable { mutableStateOf("") }
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
            ArtworkPlaceholder(
                label = "Register",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                test_tag = "register_illustration_image"
            )
            RoundedInputField(username, { username = it }, "Buat nama pengguna", Modifier.testTag("register_username_input"))
            RoundedInputField(phone, { phone = it }, "Masukan no. handphone", Modifier.testTag("register_phone_input"))
            RoundedInputField(gender, { gender = it }, "Jenis Kelamin", Modifier.testTag("register_gender_input"))
            RoundedInputField(birth_date, { birth_date = it }, "Tanggal lahir", Modifier.testTag("register_birth_date_input"))
            PasswordInputField(
                value = password,
                on_value_change = { password = it },
                placeholder_text = "Buat kata sandi",
                is_password_visible = password_visible,
                on_visibility_toggle = { password_visible = !password_visible },
                modifier = Modifier.testTag("register_password_input")
            )
            PasswordInputField(
                value = confirm_password,
                on_value_change = { confirm_password = it },
                placeholder_text = "Konfirmasi kata sandi",
                is_password_visible = confirm_visible,
                on_visibility_toggle = { confirm_visible = !confirm_visible },
                modifier = Modifier.testTag("register_confirm_password_input")
            )
            PrimaryButton(
                text = "Daftar",
                on_click = on_register_success,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("register_button")
            )
            SecondaryTextLink(
                text = "Sudah punya akun? Masuk",
                on_click = on_login,
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
private fun RegisterScreenPreview() {
    MulaTheme { RegisterScreen(state = RegisterScreenState()) }
}
