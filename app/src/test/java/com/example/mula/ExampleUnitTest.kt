package com.example.mula

import androidx.compose.ui.graphics.Color
import com.example.mula.navigation.ARG_BRANCH_ID
import com.example.mula.navigation.ARG_ENTRY_SOURCE
import com.example.mula.navigation.ARG_ORDER_ID
import com.example.mula.navigation.ARG_ORDER_METHOD
import com.example.mula.navigation.ARG_PAYMENT_ID
import com.example.mula.navigation.ARG_PHONE_NUMBER
import com.example.mula.navigation.ARG_PRODUCT_ID
import com.example.mula.navigation.ROUTE_AUTH_LANDING
import com.example.mula.navigation.ROUTE_CATALOG
import com.example.mula.navigation.ROUTE_CHECKOUT
import com.example.mula.navigation.ROUTE_FORGOT_PASSWORD
import com.example.mula.navigation.ROUTE_HOME
import com.example.mula.navigation.ROUTE_LOCATION_PICKER
import com.example.mula.navigation.ROUTE_LOGIN
import com.example.mula.navigation.ROUTE_NOTIFICATION
import com.example.mula.navigation.ROUTE_ONBOARDING
import com.example.mula.navigation.ROUTE_ORDER_HISTORY
import com.example.mula.navigation.ROUTE_ORDER_HISTORY_FAILED_DETAIL
import com.example.mula.navigation.ROUTE_ORDER_HISTORY_SUCCESS_DETAIL
import com.example.mula.navigation.ROUTE_ORDER_METHOD_SHEET
import com.example.mula.navigation.ROUTE_ORDER_STATUS_DELIVERY
import com.example.mula.navigation.ROUTE_ORDER_STATUS_PICKUP
import com.example.mula.navigation.ROUTE_OTP_VERIFICATION
import com.example.mula.navigation.ROUTE_PAYMENT_METHOD
import com.example.mula.navigation.ROUTE_PRODUCT_DETAIL
import com.example.mula.navigation.ROUTE_PROFILE
import com.example.mula.navigation.ROUTE_QRIS_PAYMENT
import com.example.mula.navigation.ROUTE_REGISTER
import com.example.mula.navigation.ROUTE_RESET_PASSWORD
import com.example.mula.navigation.ROUTE_REWARDS
import com.example.mula.navigation.ROUTE_SPLASH
import com.example.mula.navigation.ROUTE_STORE_SELECTION
import com.example.mula.navigation.ROUTE_VOUCHER
import com.example.mula.navigation.ROUTE_CATALOG_WITH_ARGS
import com.example.mula.navigation.ROUTE_CHECKOUT_WITH_ARGS
import com.example.mula.navigation.ROUTE_ORDER_HISTORY_FAILED_DETAIL_WITH_ARGS
import com.example.mula.navigation.ROUTE_ORDER_HISTORY_SUCCESS_DETAIL_WITH_ARGS
import com.example.mula.navigation.ROUTE_ORDER_STATUS_DELIVERY_WITH_ARGS
import com.example.mula.navigation.ROUTE_ORDER_STATUS_PICKUP_WITH_ARGS
import com.example.mula.navigation.ROUTE_OTP_VERIFICATION_WITH_ARGS
import com.example.mula.navigation.ROUTE_PRODUCT_DETAIL_WITH_ARGS
import com.example.mula.navigation.ROUTE_QRIS_PAYMENT_WITH_ARGS
import com.example.mula.navigation.ROUTE_STORE_SELECTION_WITH_ARGS
import com.example.mula.navigation.ROUTE_VOUCHER_WITH_ARGS
import com.example.mula.data.model.AppSession
import com.example.mula.ui.theme.MulaDimens
import com.example.mula.ui.theme.active_tab_text_color
import com.example.mula.ui.theme.app_background_color
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.default_divider_color
import com.example.mula.ui.theme.headline_accent_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.muted_text_color
import com.example.mula.ui.theme.primary_button_background_color
import com.example.mula.ui.theme.primary_button_text_color
import com.example.mula.ui.theme.screen_surface_color
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun route_constants_match_prd() {
        val routes = listOf(
            ROUTE_SPLASH,
            ROUTE_ONBOARDING,
            ROUTE_AUTH_LANDING,
            ROUTE_LOGIN,
            ROUTE_REGISTER,
            ROUTE_FORGOT_PASSWORD,
            ROUTE_OTP_VERIFICATION,
            ROUTE_RESET_PASSWORD,
            ROUTE_HOME,
            ROUTE_REWARDS,
            ROUTE_NOTIFICATION,
            ROUTE_CATALOG,
            ROUTE_STORE_SELECTION,
            ROUTE_PRODUCT_DETAIL,
            ROUTE_CHECKOUT,
            ROUTE_ORDER_METHOD_SHEET,
            ROUTE_LOCATION_PICKER,
            ROUTE_PAYMENT_METHOD,
            ROUTE_QRIS_PAYMENT,
            ROUTE_VOUCHER,
            ROUTE_ORDER_HISTORY,
            ROUTE_ORDER_HISTORY_SUCCESS_DETAIL,
            ROUTE_ORDER_HISTORY_FAILED_DETAIL,
            ROUTE_ORDER_STATUS_PICKUP,
            ROUTE_ORDER_STATUS_DELIVERY,
            ROUTE_PROFILE
        )

        assertEquals(26, routes.size)
        assertEquals("splash_screen", ROUTE_SPLASH)
        assertEquals("order_method_sheet", ROUTE_ORDER_METHOD_SHEET)
        assertTrue(routes.all { it == it.lowercase() })
        assertEquals(routes.size, routes.toSet().size)
    }

    @Test
    fun argument_constants_match_prd() {
        assertEquals("order_method", ARG_ORDER_METHOD)
        assertEquals("product_id", ARG_PRODUCT_ID)
        assertEquals("branch_id", ARG_BRANCH_ID)
        assertEquals("order_id", ARG_ORDER_ID)
        assertEquals("entry_source", ARG_ENTRY_SOURCE)
        assertEquals("phone_number", ARG_PHONE_NUMBER)
        assertEquals("payment_id", ARG_PAYMENT_ID)
    }

    @Test
    fun route_patterns_match_prd() {
        assertEquals("catalog_screen/{order_method}", ROUTE_CATALOG_WITH_ARGS)
        assertEquals("store_selection_screen/{order_method}", ROUTE_STORE_SELECTION_WITH_ARGS)
        assertEquals("product_detail_screen/{product_id}", ROUTE_PRODUCT_DETAIL_WITH_ARGS)
        assertEquals("checkout_screen/{order_method}", ROUTE_CHECKOUT_WITH_ARGS)
        assertEquals("otp_verification_screen/{phone_number}", ROUTE_OTP_VERIFICATION_WITH_ARGS)
        assertEquals("voucher_screen/{entry_source}", ROUTE_VOUCHER_WITH_ARGS)
        assertEquals("qris_payment_screen/{payment_id}", ROUTE_QRIS_PAYMENT_WITH_ARGS)
        assertEquals("order_history_success_detail_screen/{order_id}", ROUTE_ORDER_HISTORY_SUCCESS_DETAIL_WITH_ARGS)
        assertEquals("order_history_failed_detail_screen/{order_id}", ROUTE_ORDER_HISTORY_FAILED_DETAIL_WITH_ARGS)
        assertEquals("order_status_pickup_screen/{order_id}", ROUTE_ORDER_STATUS_PICKUP_WITH_ARGS)
        assertEquals("order_status_delivery_screen/{order_id}", ROUTE_ORDER_STATUS_DELIVERY_WITH_ARGS)
    }

    @Test
    fun semantic_tokens_map_to_prd_values() {
        assertEquals(Color(0xFFDFE8F6), app_background_color)
        assertEquals(Color(0xFFFFFFFF), screen_surface_color)
        assertEquals(Color(0xFF99420C), primary_button_background_color)
        assertEquals(Color(0xFFFFFFFF), primary_button_text_color)
        assertEquals(Color(0xFF7D3306), headline_accent_color)
        assertEquals(Color(0xFF6A2E08), active_tab_text_color)
        assertEquals(Color(0xFF000000), body_text_color)
        assertEquals(Color(0xFFA6A6A6), muted_text_color)
        assertEquals(Color(0xFFD3D3D3), default_divider_color)
    }

    @Test
    fun spacing_and_dimens_foundation_exist() {
        assertEquals(24, mula_spacing.xl)
        assertEquals(40, mula_spacing.xxxl)
        assertEquals(24f, MulaDimens.screen_horizontal_padding.value)
        assertEquals(16f, MulaDimens.screen_inner_padding.value)
        assertTrue(MulaDimens.primary_button_height.value > 0f)
        assertTrue(MulaDimens.bottom_tab_bar_height.value > 0f)
    }

    @Test
    fun app_session_model_matches_prd() {
        val session = AppSession(
            token = "token",
            refresh_token = "refresh",
            user_id = "user_1",
            is_logged_in = true,
            is_first_launch = false
        )

        assertEquals("token", session.token)
        assertEquals("refresh", session.refresh_token)
        assertEquals("user_1", session.user_id)
        assertTrue(session.is_logged_in)
    }
}
