package com.example.mula.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mula.ui.AuthLandingScreenRoute
import com.example.mula.ui.CatalogScreenRoute
import com.example.mula.ui.CheckoutScreenRoute
import com.example.mula.ui.ForgotPasswordScreenRoute
import com.example.mula.ui.HomeScreenRoute
import com.example.mula.ui.LocationPickerScreenRoute
import com.example.mula.ui.LoginScreenRoute
import com.example.mula.ui.NotificationScreenRoute
import com.example.mula.ui.OnboardingScreenRoute
import com.example.mula.ui.OrderHistoryFailedDetailScreenRoute
import com.example.mula.ui.OrderHistoryScreenRoute
import com.example.mula.ui.OrderHistorySuccessDetailScreenRoute
import com.example.mula.ui.OrderMethodSheetScreenRoute
import com.example.mula.ui.OrderStatusDeliveryScreenRoute
import com.example.mula.ui.OrderStatusPickupScreenRoute
import com.example.mula.ui.OtpVerificationScreenRoute
import com.example.mula.ui.PaymentMethodScreenRoute
import com.example.mula.ui.ProductDetailScreenRoute
import com.example.mula.ui.ProfileScreenRoute
import com.example.mula.ui.QrisPaymentScreenRoute
import com.example.mula.ui.RegisterScreenRoute
import com.example.mula.ui.ResetPasswordScreenRoute
import com.example.mula.ui.RewardsScreenRoute
import com.example.mula.ui.SplashScreenRoute
import com.example.mula.ui.StoreSelectionScreenRoute
import com.example.mula.ui.VoucherScreenRoute

@Composable
fun MulaNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH
) {
    var has_seen_onboarding by rememberSaveable { mutableStateOf(false) }
    var is_logged_in by rememberSaveable { mutableStateOf(false) }
    var selected_order_method by rememberSaveable { mutableStateOf("delivery") }
    var selected_branch by rememberSaveable { mutableStateOf("MULA Cirendeu") }
    var selected_location by rememberSaveable { mutableStateOf("Lokasi New Grand Cirendeu Royal") }
    var selected_voucher by rememberSaveable { mutableStateOf("Pakai voucher untuk lebih hemat") }
    val active_order_id = "0001"

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(ROUTE_SPLASH) {
            SplashScreenRoute(
                has_seen_onboarding = has_seen_onboarding,
                is_logged_in = is_logged_in,
                on_splash_complete = { target ->
                    navController.navigate(target) { popUpTo(ROUTE_SPLASH) { inclusive = true } }
                }
            )
        }
        composable(ROUTE_ONBOARDING) {
            OnboardingScreenRoute(
                on_finish = {
                    has_seen_onboarding = true
                    navController.navigate(ROUTE_AUTH_LANDING) {
                        popUpTo(ROUTE_ONBOARDING) { inclusive = true }
                    }
                }
            )
        }
        composable(ROUTE_AUTH_LANDING) {
            AuthLandingScreenRoute(
                on_login = { navController.navigate(ROUTE_LOGIN) },
                on_register = { navController.navigate(ROUTE_REGISTER) }
            )
        }
        composable(ROUTE_LOGIN) {
            LoginScreenRoute(
                on_login_success = {
                    is_logged_in = true
                    navController.navigate(ROUTE_HOME) {
                        popUpTo(ROUTE_AUTH_LANDING) { inclusive = true }
                    }
                },
                on_forgot_password = { navController.navigate(ROUTE_FORGOT_PASSWORD) },
                on_register = { navController.navigate(ROUTE_REGISTER) }
            )
        }
        composable(ROUTE_REGISTER) {
            RegisterScreenRoute(
                on_login = { navController.navigate(ROUTE_LOGIN) },
                on_register_success = {
                    navController.navigate(ROUTE_LOGIN) { popUpTo(ROUTE_REGISTER) { inclusive = true } }
                }
            )
        }
        composable(ROUTE_FORGOT_PASSWORD) {
            ForgotPasswordScreenRoute(
                on_submit = { phone -> navController.navigate(otp_verification_route(phone)) },
                on_login = { navController.navigate(ROUTE_LOGIN) { launchSingleTop = true } }
            )
        }
        composable(
            route = ROUTE_OTP_VERIFICATION_WITH_ARGS,
            arguments = listOf(navArgument(ARG_PHONE_NUMBER) { type = NavType.StringType })
        ) { back_stack_entry ->
            OtpVerificationScreenRoute(
                phone_number = back_stack_entry.arguments?.getString(ARG_PHONE_NUMBER).orEmpty(),
                on_verified = { navController.navigate(ROUTE_RESET_PASSWORD) },
                on_resend = {}
            )
        }
        composable(ROUTE_RESET_PASSWORD) {
            ResetPasswordScreenRoute(
                on_success = {
                    navController.navigate(ROUTE_LOGIN) { popUpTo(ROUTE_FORGOT_PASSWORD) { inclusive = true } }
                }
            )
        }
        composable(ROUTE_HOME) {
            HomeScreenRoute(
                on_notification = { navController.navigate(ROUTE_NOTIFICATION) },
                on_rewards = { navController.navigate(ROUTE_REWARDS) },
                on_delivery = {
                    selected_order_method = "delivery"
                    navController.navigate(catalog_route("delivery"))
                },
                on_pickup = {
                    selected_order_method = "pickup"
                    navController.navigate(catalog_route("pickup"))
                },
                on_tab_selected = { route -> navController.navigate(route) { launchSingleTop = true } }
            )
        }
        composable(ROUTE_REWARDS) { RewardsScreenRoute() }
        composable(ROUTE_NOTIFICATION) { NotificationScreenRoute() }
        composable(
            route = ROUTE_CATALOG_WITH_ARGS,
            arguments = listOf(navArgument(ARG_ORDER_METHOD) { type = NavType.StringType; defaultValue = "delivery" })
        ) { back_stack_entry ->
            val order_method = back_stack_entry.arguments?.getString(ARG_ORDER_METHOD).orEmpty().ifBlank { selected_order_method }
            CatalogScreenRoute(
                order_method = order_method,
                on_back = { navController.popBackStack() },
                on_branch = { navController.navigate(store_selection_route(order_method)) },
                on_product = { navController.navigate(product_detail_route("creamy_latte")) }
            )
        }
        composable(
            route = ROUTE_STORE_SELECTION_WITH_ARGS,
            arguments = listOf(navArgument(ARG_ORDER_METHOD) { type = NavType.StringType })
        ) { back_stack_entry ->
            val order_method = back_stack_entry.arguments?.getString(ARG_ORDER_METHOD).orEmpty().ifBlank { selected_order_method }
            StoreSelectionScreenRoute(
                order_method = order_method,
                on_back = { navController.popBackStack() },
                on_change_method = { navController.navigate(ROUTE_ORDER_METHOD_SHEET) },
                on_branch_selected = { branch ->
                    selected_branch = branch
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = ROUTE_PRODUCT_DETAIL_WITH_ARGS,
            arguments = listOf(navArgument(ARG_PRODUCT_ID) { type = NavType.StringType })
        ) { back_stack_entry ->
            ProductDetailScreenRoute(
                product_id = back_stack_entry.arguments?.getString(ARG_PRODUCT_ID).orEmpty(),
                on_back = { navController.popBackStack() },
                on_buy = { navController.navigate(checkout_route(selected_order_method)) }
            )
        }
        composable(
            route = ROUTE_CHECKOUT_WITH_ARGS,
            arguments = listOf(navArgument(ARG_ORDER_METHOD) { type = NavType.StringType; defaultValue = "delivery" })
        ) { back_stack_entry ->
            val order_method = back_stack_entry.arguments?.getString(ARG_ORDER_METHOD).orEmpty().ifBlank { selected_order_method }
            selected_order_method = order_method
            CheckoutScreenRoute(
                order_method = order_method,
                selected_branch = selected_branch,
                selected_location = selected_location,
                selected_voucher = selected_voucher,
                on_back = { navController.popBackStack() },
                on_change_method = { navController.navigate(ROUTE_ORDER_METHOD_SHEET) },
                on_select_branch = { navController.navigate(store_selection_route(order_method)) },
                on_select_location = { navController.navigate(ROUTE_LOCATION_PICKER) },
                on_select_voucher = { navController.navigate(voucher_route("checkout")) },
                on_continue_payment = { navController.navigate(ROUTE_PAYMENT_METHOD) }
            )
        }
        composable(ROUTE_ORDER_METHOD_SHEET) {
            OrderMethodSheetScreenRoute(
                on_select_delivery = {
                    selected_order_method = "delivery"
                    navController.popBackStack()
                },
                on_select_pickup = {
                    selected_order_method = "pickup"
                    navController.popBackStack()
                }
            )
        }
        composable(ROUTE_LOCATION_PICKER) {
            LocationPickerScreenRoute(
                on_back = { navController.popBackStack() },
                on_confirm = { location ->
                    selected_location = location
                    navController.popBackStack()
                }
            )
        }
        composable(ROUTE_PAYMENT_METHOD) {
            PaymentMethodScreenRoute(
                on_back = { navController.popBackStack() },
                on_payment_selected = {},
                on_continue = { payment ->
                    if (payment == "qris") navController.navigate(qris_payment_route(active_order_id))
                    else navController.navigate(
                        if (selected_order_method == "pickup") order_status_pickup_route(active_order_id)
                        else order_status_delivery_route(active_order_id)
                    )
                }
            )
        }
        composable(
            route = ROUTE_QRIS_PAYMENT_WITH_ARGS,
            arguments = listOf(navArgument(ARG_PAYMENT_ID) { type = NavType.StringType })
        ) { back_stack_entry ->
            QrisPaymentScreenRoute(
                payment_id = back_stack_entry.arguments?.getString(ARG_PAYMENT_ID).orEmpty(),
                on_close = { navController.popBackStack() },
                on_check_status = {
                    navController.navigate(
                        if (selected_order_method == "pickup") order_status_pickup_route(active_order_id)
                        else order_status_delivery_route(active_order_id)
                    )
                }
            )
        }
        composable(
            route = ROUTE_VOUCHER_WITH_ARGS,
            arguments = listOf(navArgument(ARG_ENTRY_SOURCE) { type = NavType.StringType; defaultValue = "tab" })
        ) { back_stack_entry ->
            VoucherScreenRoute(
                entry_source = back_stack_entry.arguments?.getString(ARG_ENTRY_SOURCE).orEmpty(),
                on_back = { navController.popBackStack() },
                on_voucher_selected = { voucher ->
                    selected_voucher = voucher
                    navController.popBackStack()
                }
            )
        }
        composable(ROUTE_ORDER_HISTORY) {
            OrderHistoryScreenRoute(
                on_success_detail = { navController.navigate(order_history_success_detail_route(active_order_id)) },
                on_failed_detail = { navController.navigate(order_history_failed_detail_route(active_order_id)) },
                on_reorder = { navController.navigate(catalog_route("delivery")) },
                on_tab_selected = { route -> navController.navigate(route) { launchSingleTop = true } }
            )
        }
        composable(
            route = ROUTE_ORDER_HISTORY_SUCCESS_DETAIL_WITH_ARGS,
            arguments = listOf(navArgument(ARG_ORDER_ID) { type = NavType.StringType; defaultValue = active_order_id })
        ) { back_stack_entry ->
            OrderHistorySuccessDetailScreenRoute(
                order_id = back_stack_entry.arguments?.getString(ARG_ORDER_ID).orEmpty(),
                on_back = { navController.popBackStack() }
            )
        }
        composable(
            route = ROUTE_ORDER_HISTORY_FAILED_DETAIL_WITH_ARGS,
            arguments = listOf(navArgument(ARG_ORDER_ID) { type = NavType.StringType; defaultValue = active_order_id })
        ) { back_stack_entry ->
            OrderHistoryFailedDetailScreenRoute(
                order_id = back_stack_entry.arguments?.getString(ARG_ORDER_ID).orEmpty(),
                on_back = { navController.popBackStack() }
            )
        }
        composable(
            route = ROUTE_ORDER_STATUS_PICKUP_WITH_ARGS,
            arguments = listOf(navArgument(ARG_ORDER_ID) { type = NavType.StringType; defaultValue = active_order_id })
        ) { back_stack_entry ->
            OrderStatusPickupScreenRoute(
                order_id = back_stack_entry.arguments?.getString(ARG_ORDER_ID).orEmpty(),
                on_back_home = { navController.navigate(ROUTE_HOME) { popUpTo(ROUTE_HOME) { inclusive = false } } }
            )
        }
        composable(
            route = ROUTE_ORDER_STATUS_DELIVERY_WITH_ARGS,
            arguments = listOf(navArgument(ARG_ORDER_ID) { type = NavType.StringType; defaultValue = active_order_id })
        ) { back_stack_entry ->
            OrderStatusDeliveryScreenRoute(
                order_id = back_stack_entry.arguments?.getString(ARG_ORDER_ID).orEmpty(),
                on_back_home = { navController.navigate(ROUTE_HOME) { popUpTo(ROUTE_HOME) { inclusive = false } } }
            )
        }
        composable(ROUTE_PROFILE) {
            ProfileScreenRoute(
                on_logout = {
                    is_logged_in = false
                    navController.navigate(ROUTE_AUTH_LANDING) { popUpTo(ROUTE_HOME) { inclusive = true } }
                },
                on_tab_selected = { route -> navController.navigate(route) { launchSingleTop = true } }
            )
        }
    }
}
