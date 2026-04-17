package com.example.mula.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.R
import com.example.mula.ui.components.PrimaryButton
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.headline_accent_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.OnboardingScreenEvent
import com.example.mula.viewmodel.OnboardingViewModel

data class OnboardingScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun OnboardingScreenRoute(
    on_finish: () -> Unit = {},
    viewModel: OnboardingViewModel = viewModel()
) {
    OnboardingScreen(
        state = OnboardingScreenState(
            is_loading = viewModel.state.is_loading,
            error_message = viewModel.state.error_message
        ),
        on_finish = on_finish
    ) {
        viewModel.on_event(OnboardingScreenEvent.OnNavigationHandled)
    }
}

@Composable
fun OnboardingScreen(
    state: OnboardingScreenState,
    on_finish: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    val context = LocalContext.current
    var current_page by rememberSaveable { mutableIntStateOf(0) }
    val pages = listOf(
        Triple("Selamat Datang!", stringResource(R.string.onboarding_page_1_description), "Onboarding 1"),
        Triple(
            "Tempat Terbaik Untuk Menikmati Kopi",
            stringResource(R.string.onboarding_page_2_description),
            "Onboarding 2"
        )
    )
    val page = pages[current_page]

    BackHandler {
        if (current_page == 0) (context as? android.app.Activity)?.finish()
        else current_page = 0
    }

    Stage4ABackground(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(stage_4a_screen_padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(mula_spacing.lg.dp)
            ) {
                ArtworkPlaceholder(
                    label = page.third,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp),
                    test_tag = "onboarding_page_image"
                )
                Text(
                    text = page.first,
                    style = MaterialTheme.typography.headlineMedium,
                    color = headline_accent_color,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.testTag("onboarding_title_text")
                )
                Text(
                    text = page.second,
                    style = MaterialTheme.typography.bodyLarge,
                    color = body_text_color,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.testTag("onboarding_description_text")
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = mula_spacing.xxl.dp, bottom = mula_spacing.md.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
            ) {
                PagerIndicator(
                    page_count = pages.size,
                    selected_index = current_page,
                    test_tag = "onboarding_pager_indicator"
                )
                if (current_page == 0) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        TextButton(
                            onClick = { current_page = 1 },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(68.dp)
                                .testTag("onboarding_next_button")
                        ) {
                            Text(">", style = MaterialTheme.typography.headlineMedium, color = headline_accent_color)
                        }
                    }
                } else {
                    PrimaryButton(
                        text = stringResource(R.string.onboarding_final_action),
                        on_click = on_finish,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("onboarding_primary_button")
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun OnboardingScreenPreview() {
    MulaTheme { OnboardingScreen(state = OnboardingScreenState()) }
}
