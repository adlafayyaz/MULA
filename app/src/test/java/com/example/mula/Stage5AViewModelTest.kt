package com.example.mula

import com.example.mula.navigation.ARG_ORDER_METHOD
import com.example.mula.navigation.ARG_PHONE_NUMBER
import com.example.mula.navigation.ROUTE_AUTH_LANDING
import com.example.mula.navigation.ROUTE_CATALOG
import com.example.mula.navigation.ROUTE_FORGOT_PASSWORD
import com.example.mula.navigation.ROUTE_HOME
import com.example.mula.navigation.ROUTE_LOGIN
import com.example.mula.navigation.ROUTE_ONBOARDING
import com.example.mula.navigation.ROUTE_OTP_VERIFICATION
import com.example.mula.navigation.ROUTE_RESET_PASSWORD
import com.example.mula.viewmodel.ENTRY_SOURCE_TAB
import com.example.mula.viewmodel.ForgotPasswordScreenEvent
import com.example.mula.viewmodel.ForgotPasswordViewModel
import com.example.mula.viewmodel.HomeScreenEvent
import com.example.mula.viewmodel.HomeViewModel
import com.example.mula.viewmodel.LoginScreenEvent
import com.example.mula.viewmodel.LoginViewModel
import com.example.mula.viewmodel.ORDER_METHOD_DELIVERY
import com.example.mula.viewmodel.OtpVerificationScreenEvent
import com.example.mula.viewmodel.OtpVerificationViewModel
import com.example.mula.viewmodel.ProfileScreenEvent
import com.example.mula.viewmodel.ProfileViewModel
import com.example.mula.viewmodel.ResetPasswordScreenEvent
import com.example.mula.viewmodel.ResetPasswordSessionStore
import com.example.mula.viewmodel.ResetPasswordViewModel
import com.example.mula.viewmodel.SplashScreenEvent
import com.example.mula.viewmodel.SplashViewModel
import com.example.mula.viewmodel.Stage5ARepositoryProvider
import com.example.mula.viewmodel.TAB_VOUCHER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class Stage5AViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun set_up() {
        Dispatchers.setMain(dispatcher)
        runTest(dispatcher) {
            Stage5ARepositoryProvider.session_repository.clear_session()
            Stage5ARepositoryProvider.session_repository.set_first_launch_completed()
        }
        ResetPasswordSessionStore.reset_token = null
    }

    @After
    fun tear_down() {
        Dispatchers.resetMain()
    }

    @Test
    fun splash_routes_to_onboarding_when_first_launch_true() = runTest(dispatcher) {
        Stage5ARepositoryProvider.session_repository.save_session(
            com.example.mula.data.model.AppSession(
                token = "",
                refresh_token = null,
                user_id = "",
                is_logged_in = false,
                is_first_launch = true
            )
        )
        val view_model = SplashViewModel()

        view_model.on_event(SplashScreenEvent.OnAppStarted)
        advanceUntilIdle()

        assertEquals(ROUTE_ONBOARDING, view_model.state.navigation_target)
    }

    @Test
    fun login_blocks_invalid_form_then_saves_session_on_success() = runTest(dispatcher) {
        val view_model = LoginViewModel()

        view_model.on_event(LoginScreenEvent.OnLoginClicked)
        assertEquals("Nama pengguna wajib diisi", view_model.state.username_error)
        assertEquals("Kata sandi wajib diisi", view_model.state.password_error)

        view_model.on_event(LoginScreenEvent.OnUsernameChanged("mula_user"))
        view_model.on_event(LoginScreenEvent.OnPasswordChanged("password123"))
        view_model.on_event(LoginScreenEvent.OnLoginClicked)
        advanceUntilIdle()

        assertEquals(ROUTE_HOME, view_model.state.navigation_target)
        assertEquals(ROUTE_AUTH_LANDING, view_model.state.clear_back_stack_to_route)
        assertTrue(Stage5ARepositoryProvider.session_repository.get_session().is_logged_in)
    }

    @Test
    fun forgot_password_passes_phone_arg() = runTest(dispatcher) {
        val view_model = ForgotPasswordViewModel()

        view_model.on_event(ForgotPasswordScreenEvent.OnPhoneNumberChanged("081234567890"))
        view_model.on_event(ForgotPasswordScreenEvent.OnSubmitClicked)
        advanceUntilIdle()

        assertEquals(ROUTE_OTP_VERIFICATION, view_model.state.navigation_target)
        assertEquals("081234567890", view_model.state.navigation_argument_map[ARG_PHONE_NUMBER])
    }

    @Test
    fun otp_auto_verify_routes_to_reset_password() = runTest(dispatcher) {
        val view_model = OtpVerificationViewModel()
        view_model.bind_phone_number("081234567890")

        view_model.on_event(OtpVerificationScreenEvent.OnOtpDigitChanged(0, "1"))
        view_model.on_event(OtpVerificationScreenEvent.OnOtpDigitChanged(1, "2"))
        view_model.on_event(OtpVerificationScreenEvent.OnOtpDigitChanged(2, "3"))
        view_model.on_event(OtpVerificationScreenEvent.OnOtpDigitChanged(3, "4"))
        advanceUntilIdle()

        assertEquals(ROUTE_RESET_PASSWORD, view_model.state.navigation_target)
        assertEquals("reset_token_1", ResetPasswordSessionStore.reset_token)
    }

    @Test
    fun reset_password_requires_token_and_clears_stack_on_success() = runTest(dispatcher) {
        ResetPasswordSessionStore.reset_token = "reset_token_1"
        val view_model = ResetPasswordViewModel()

        view_model.on_event(ResetPasswordScreenEvent.OnNewPasswordChanged("password123"))
        view_model.on_event(ResetPasswordScreenEvent.OnConfirmPasswordChanged("password123"))
        view_model.on_event(ResetPasswordScreenEvent.OnSubmitClicked)
        advanceUntilIdle()

        assertEquals(ROUTE_LOGIN, view_model.state.navigation_target)
        assertEquals(ROUTE_FORGOT_PASSWORD, view_model.state.clear_back_stack_to_route)
        assertEquals(null, ResetPasswordSessionStore.reset_token)
    }

    @Test
    fun home_tab_navigation_uses_viewmodel_state() = runTest(dispatcher) {
        val view_model = HomeViewModel()

        view_model.on_event(HomeScreenEvent.OnScreenOpened)
        advanceUntilIdle()
        view_model.on_event(HomeScreenEvent.OnDeliveryClicked)

        assertEquals(ROUTE_CATALOG, view_model.state.navigation_target)
        assertEquals(ORDER_METHOD_DELIVERY, view_model.state.navigation_argument_map[ARG_ORDER_METHOD])

        view_model.on_event(HomeScreenEvent.OnNavigationHandled)
        view_model.on_event(HomeScreenEvent.OnTabSelected(TAB_VOUCHER))

        assertEquals("voucher_screen", view_model.state.navigation_target)
        assertEquals(ENTRY_SOURCE_TAB, view_model.state.navigation_argument_map["entry_source"])
    }

    @Test
    fun profile_logout_clears_session_even_if_remote_call_runs() = runTest(dispatcher) {
        Stage5ARepositoryProvider.session_repository.save_session(
            com.example.mula.data.model.AppSession(
                token = "token",
                refresh_token = "refresh",
                user_id = "user_1",
                is_logged_in = true,
                is_first_launch = false
            )
        )
        val view_model = ProfileViewModel()

        view_model.on_event(ProfileScreenEvent.OnLogoutClicked)
        advanceUntilIdle()

        assertEquals(ROUTE_AUTH_LANDING, view_model.state.navigation_target)
        assertFalse(Stage5ARepositoryProvider.session_repository.get_session().is_logged_in)
    }

    @Test
    fun otp_resend_unlocks_after_timer() = runTest(dispatcher) {
        val view_model = OtpVerificationViewModel()
        view_model.bind_phone_number("081234567890")

        advanceTimeBy(30000)
        advanceUntilIdle()

        assertTrue(view_model.state.can_resend)
    }
}
