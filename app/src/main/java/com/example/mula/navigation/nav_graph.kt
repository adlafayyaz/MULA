package com.example.mula.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.NavHostController
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
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(ROUTE_SPLASH) { SplashScreenRoute() }
        composable(ROUTE_ONBOARDING) { OnboardingScreenRoute() }
        composable(ROUTE_AUTH_LANDING) { AuthLandingScreenRoute() }
        composable(ROUTE_LOGIN) { LoginScreenRoute() }
        composable(ROUTE_REGISTER) { RegisterScreenRoute() }
        composable(ROUTE_FORGOT_PASSWORD) { ForgotPasswordScreenRoute() }
        composable(
            route = ROUTE_OTP_VERIFICATION_WITH_ARGS,
            arguments = listOf(navArgument(ARG_PHONE_NUMBER) { type = NavType.StringType })
        ) { back_stack_entry ->
            OtpVerificationScreenRoute(
                phone_number = back_stack_entry.arguments?.getString(ARG_PHONE_NUMBER).orEmpty()
            )
        }
        composable(ROUTE_RESET_PASSWORD) { ResetPasswordScreenRoute() }
        composable(ROUTE_HOME) { HomeScreenRoute() }
        composable(ROUTE_REWARDS) { RewardsScreenRoute() }
        composable(ROUTE_NOTIFICATION) { NotificationScreenRoute() }
        composable(
            route = ROUTE_CATALOG_WITH_ARGS,
            arguments = listOf(navArgument(ARG_ORDER_METHOD) { type = NavType.StringType })
        ) { back_stack_entry ->
            CatalogScreenRoute(
                order_method = back_stack_entry.arguments?.getString(ARG_ORDER_METHOD).orEmpty()
            )
        }
        composable(
            route = ROUTE_STORE_SELECTION_WITH_ARGS,
            arguments = listOf(navArgument(ARG_ORDER_METHOD) { type = NavType.StringType })
        ) { back_stack_entry ->
            StoreSelectionScreenRoute(
                order_method = back_stack_entry.arguments?.getString(ARG_ORDER_METHOD).orEmpty()
            )
        }
        composable(
            route = ROUTE_PRODUCT_DETAIL_WITH_ARGS,
            arguments = listOf(navArgument(ARG_PRODUCT_ID) { type = NavType.StringType })
        ) { back_stack_entry ->
            ProductDetailScreenRoute(
                product_id = back_stack_entry.arguments?.getString(ARG_PRODUCT_ID).orEmpty()
            )
        }
        composable(
            route = ROUTE_CHECKOUT_WITH_ARGS,
            arguments = listOf(navArgument(ARG_ORDER_METHOD) { type = NavType.StringType })
        ) { back_stack_entry ->
            CheckoutScreenRoute(
                order_method = back_stack_entry.arguments?.getString(ARG_ORDER_METHOD).orEmpty()
            )
        }
        composable(ROUTE_ORDER_METHOD_SHEET) { OrderMethodSheetScreenRoute() }
        composable(ROUTE_LOCATION_PICKER) { LocationPickerScreenRoute() }
        composable(ROUTE_PAYMENT_METHOD) { PaymentMethodScreenRoute() }
        composable(
            route = ROUTE_QRIS_PAYMENT_WITH_ARGS,
            arguments = listOf(navArgument(ARG_PAYMENT_ID) { type = NavType.StringType })
        ) { back_stack_entry ->
            QrisPaymentScreenRoute(
                payment_id = back_stack_entry.arguments?.getString(ARG_PAYMENT_ID).orEmpty()
            )
        }
        composable(
            route = ROUTE_VOUCHER_WITH_ARGS,
            arguments = listOf(navArgument(ARG_ENTRY_SOURCE) { type = NavType.StringType })
        ) { back_stack_entry ->
            VoucherScreenRoute(
                entry_source = back_stack_entry.arguments?.getString(ARG_ENTRY_SOURCE).orEmpty()
            )
        }
        composable(ROUTE_ORDER_HISTORY) { OrderHistoryScreenRoute() }
        composable(
            route = ROUTE_ORDER_HISTORY_SUCCESS_DETAIL_WITH_ARGS,
            arguments = listOf(navArgument(ARG_ORDER_ID) { type = NavType.StringType })
        ) { back_stack_entry ->
            OrderHistorySuccessDetailScreenRoute(
                order_id = back_stack_entry.arguments?.getString(ARG_ORDER_ID).orEmpty()
            )
        }
        composable(
            route = ROUTE_ORDER_HISTORY_FAILED_DETAIL_WITH_ARGS,
            arguments = listOf(navArgument(ARG_ORDER_ID) { type = NavType.StringType })
        ) { back_stack_entry ->
            OrderHistoryFailedDetailScreenRoute(
                order_id = back_stack_entry.arguments?.getString(ARG_ORDER_ID).orEmpty()
            )
        }
        composable(
            route = ROUTE_ORDER_STATUS_PICKUP_WITH_ARGS,
            arguments = listOf(navArgument(ARG_ORDER_ID) { type = NavType.StringType })
        ) { back_stack_entry ->
            OrderStatusPickupScreenRoute(
                order_id = back_stack_entry.arguments?.getString(ARG_ORDER_ID).orEmpty()
            )
        }
        composable(
            route = ROUTE_ORDER_STATUS_DELIVERY_WITH_ARGS,
            arguments = listOf(navArgument(ARG_ORDER_ID) { type = NavType.StringType })
        ) { back_stack_entry ->
            OrderStatusDeliveryScreenRoute(
                order_id = back_stack_entry.arguments?.getString(ARG_ORDER_ID).orEmpty()
            )
        }
        composable(ROUTE_PROFILE) { ProfileScreenRoute() }
    }
}
