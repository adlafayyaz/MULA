package com.example.mula.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.components.SecondaryTextLink
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.default_divider_color
import com.example.mula.ui.theme.headline_accent_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.screen_surface_color
import com.example.mula.viewmodel.OtpVerificationScreenEvent
import com.example.mula.viewmodel.OtpVerificationViewModel

data class OtpVerificationScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun OtpVerificationScreenRoute(
    phone_number: String = "+62 813-7783-6098",
    on_verified: () -> Unit = {},
    on_resend: () -> Unit = {},
    viewModel: OtpVerificationViewModel = viewModel()
) {
    OtpVerificationScreen(
        state = OtpVerificationScreenState(
            is_loading = viewModel.state.is_loading,
            error_message = viewModel.state.error_message
        ),
        phone_number = if (phone_number.isBlank()) "+62 813-7783-6098" else phone_number,
        on_verified = on_verified,
        on_resend = on_resend
    ) { viewModel.on_event(OtpVerificationScreenEvent.OnErrorMessageDismissed) }
}

@Composable
fun OtpVerificationScreen(
    state: OtpVerificationScreenState,
    phone_number: String = "+62 813-7783-6098",
    on_verified: () -> Unit = {},
    on_resend: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    val otp_values = remember { mutableStateListOf("", "", "", "") }

    Stage4ABackground(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(stage_4a_screen_padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(mula_spacing.lg.dp)
        ) {
            Column(
                modifier = Modifier.padding(top = 56.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp)
            ) {
                Text(
                    text = "Masukan kode Verifikasi",
                    style = MaterialTheme.typography.headlineMedium,
                    color = headline_accent_color,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.testTag("otp_title_text")
                )
                Text(
                    text = "Kami telah mengirimkan kode ke $phone_number",
                    style = MaterialTheme.typography.bodyLarge,
                    color = body_text_color,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.testTag("otp_description_text")
                )
            }
            Row(
                modifier = Modifier.testTag("otp_input_row"),
                horizontalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp)
            ) {
                otp_values.forEachIndexed { index, value ->
                    BasicTextField(
                        value = value,
                        onValueChange = { otp_values[index] = it.take(1).filter(Char::isDigit) },
                        modifier = Modifier
                            .size(60.dp)
                            .background(screen_surface_color, RoundedCornerShape(16.dp))
                            .border(1.dp, default_divider_color, RoundedCornerShape(16.dp))
                            .padding(top = 18.dp)
                            .testTag("otp_digit_${index + 1}_input"),
                        textStyle = MaterialTheme.typography.headlineMedium.copy(
                            color = body_text_color,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        decorationBox = { inner_text_field ->
                            Box(contentAlignment = Alignment.Center) { inner_text_field() }
                        }
                    )
                }
            }
            SecondaryTextLink(
                text = "Tidak menerima kode? Kirim Ulang",
                on_click = on_resend,
                modifier = Modifier.testTag("resend_otp_button"),
                text_align = TextAlign.Center
            )
            androidx.compose.material3.TextButton(onClick = on_verified) { Text("Lanjut") }
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun OtpVerificationScreenPreview() {
    MulaTheme { OtpVerificationScreen(state = OtpVerificationScreenState()) }
}
