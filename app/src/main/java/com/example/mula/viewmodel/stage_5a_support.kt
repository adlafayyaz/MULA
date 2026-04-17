package com.example.mula.viewmodel

import com.example.mula.data.datasource.FakeAuthDataSource
import com.example.mula.data.datasource.FakeHomeDataSource
import com.example.mula.data.datasource.FakeNotificationDataSource
import com.example.mula.data.datasource.FakeOrdersDataSource
import com.example.mula.data.datasource.FakeProfileDataSource
import com.example.mula.data.datasource.FakeRewardsDataSource
import com.example.mula.data.datasource.FakeSessionDataSource
import com.example.mula.data.model.AppSession
import com.example.mula.data.model.Voucher
import com.example.mula.data.repository.AuthRepository
import com.example.mula.data.repository.HomeRepository
import com.example.mula.data.repository.NotificationRepository
import com.example.mula.data.repository.OrdersRepository
import com.example.mula.data.repository.ProfileRepository
import com.example.mula.data.repository.RewardsRepository
import com.example.mula.data.repository.SessionRepository
import com.example.mula.navigation.ROUTE_HOME
import com.example.mula.navigation.ROUTE_ORDER_HISTORY
import com.example.mula.navigation.ROUTE_PROFILE
import com.example.mula.navigation.ROUTE_VOUCHER
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

internal const val TAB_HOME = "home"
internal const val TAB_VOUCHER = "voucher"
internal const val TAB_ORDER_HISTORY = "order_history"
internal const val TAB_PROFILE = "profile"
internal const val ENTRY_SOURCE_TAB = "tab"
internal const val ENTRY_SOURCE_CHECKOUT = "checkout"
internal const val ORDER_METHOD_DELIVERY = "delivery"
internal const val ORDER_METHOD_PICKUP = "pickup"

internal object Stage5ARepositoryProvider {
    private val auth_data_source = FakeAuthDataSource()
    private val session_data_source = FakeSessionDataSource()
    private val home_data_source = FakeHomeDataSource()
    private val rewards_data_source = FakeRewardsDataSource()
    private val notification_data_source = FakeNotificationDataSource()
    private val orders_data_source = FakeOrdersDataSource()
    private val profile_data_source = FakeProfileDataSource()

    val auth_repository: AuthRepository = object : AuthRepository {
        override suspend fun login(username: String, password: String) =
            auth_data_source.login(username = username, password = password)

        override suspend fun register(
            username: String,
            phone_number: String,
            gender: String,
            birth_date: String,
            password: String
        ) = auth_data_source.register(
            username = username,
            phone_number = phone_number,
            gender = gender,
            birth_date = birth_date,
            password = password
        )

        override suspend fun request_password_reset(phone_number: String) =
            auth_data_source.request_password_reset(phone_number = phone_number)

        override suspend fun verify_otp(phone_number: String, otp_code: String) =
            auth_data_source.verify_otp(phone_number = phone_number, otp_code = otp_code)

        override suspend fun reset_password(reset_token: String, new_password: String) =
            auth_data_source.reset_password(reset_token = reset_token, new_password = new_password)

        override suspend fun logout() = auth_data_source.logout()
    }

    val session_repository: SessionRepository = object : SessionRepository {
        override suspend fun get_session(): AppSession = session_data_source.get_session()

        override suspend fun save_session(session: AppSession) {
            session_data_source.save_session(session = session)
        }

        override suspend fun clear_session() {
            session_data_source.clear_session()
        }

        override suspend fun set_first_launch_completed() {
            session_data_source.set_first_launch_completed()
        }

        override suspend fun is_first_launch(): Boolean = session_data_source.is_first_launch()
    }

    val home_repository: HomeRepository = object : HomeRepository {
        override suspend fun get_banners() = home_data_source.get_banners()
        override suspend fun get_promos() = home_data_source.get_promos()
        override suspend fun get_token_balance() = home_data_source.get_token_balance()
        override suspend fun get_greeting_name() = home_data_source.get_greeting_name()
    }

    val rewards_repository: RewardsRepository = object : RewardsRepository {
        override suspend fun get_rewards() = rewards_data_source.get_rewards()
        override suspend fun get_reward_filters() = rewards_data_source.get_reward_filters()
        override suspend fun get_token_balance() = rewards_data_source.get_token_balance()
        override suspend fun redeem_reward(reward_id: String) = rewards_data_source.redeem_reward(reward_id = reward_id)
    }

    val notification_repository: NotificationRepository = object : NotificationRepository {
        override suspend fun get_notifications() = notification_data_source.get_notifications()
        override suspend fun mark_as_read(notification_id: String) =
            notification_data_source.mark_as_read(notification_id = notification_id)
    }

    val orders_repository: OrdersRepository = object : OrdersRepository {
        override suspend fun get_orders() = orders_data_source.get_orders()
        override suspend fun get_order_detail(order_id: String) = orders_data_source.get_order_detail(order_id = order_id)
        override suspend fun submit_rating(order_id: String, rating: Int) =
            orders_data_source.submit_rating(order_id = order_id, rating = rating)

        override suspend fun reorder(order_id: String) = orders_data_source.reorder(order_id = order_id)
    }

    val profile_repository: ProfileRepository = object : ProfileRepository {
        override suspend fun get_profile() = profile_data_source.get_profile()
        override suspend fun update_username(username: String) = profile_data_source.update_username(username = username)
        override suspend fun update_phone_number(phone_number: String) =
            profile_data_source.update_phone_number(phone_number = phone_number)
    }

    val vouchers: List<Voucher> = listOf(
        Voucher(
            id = "voucher_1",
            name = "PerMULAan! Diskon 50%",
            description = "Voucher khusus untuk pengguna baru",
            expiry_date = "2026-06-18",
            discount_type = "percentage",
            discount_value = 50,
            minimum_purchase = 30000,
            maximum_discount = 25000,
            is_available = true
        ),
        Voucher(
            id = "voucher_2",
            name = "MULAi Aja Dulu! Diskon 25%",
            description = "Maksimum Rp25rb",
            expiry_date = "2026-06-16",
            discount_type = "percentage",
            discount_value = 25,
            minimum_purchase = 25000,
            maximum_discount = 25000,
            is_available = true
        ),
        Voucher(
            id = "voucher_3",
            name = "Beli 1 Gratis 1",
            description = "Pembelian Minimal Rp35rb",
            expiry_date = "2026-06-14",
            discount_type = "bundle",
            discount_value = 1,
            minimum_purchase = 35000,
            maximum_discount = null,
            is_available = true
        )
    )
}

internal fun String.trimmed(): String = trim()

internal fun build_token_balance_text(balance: Int): String = "$balance MULA Token"

internal fun route_for_tab(tab_id: String): String? = when (tab_id) {
    TAB_HOME -> ROUTE_HOME
    TAB_VOUCHER -> ROUTE_VOUCHER
    TAB_ORDER_HISTORY -> ROUTE_ORDER_HISTORY
    TAB_PROFILE -> ROUTE_PROFILE
    else -> null
}

internal fun is_valid_indonesian_phone(phone_number: String): Boolean {
    val normalized = phone_number.trim().replace(" ", "").replace("-", "")
    return Regex("^(\\+62|62|0)8[1-9][0-9]{6,11}$").matches(normalized)
}

internal fun is_not_future_date(raw_value: String): Boolean {
    val value = raw_value.trim()
    if (value.isBlank()) return false
    val formatters = listOf(
        DateTimeFormatter.ISO_LOCAL_DATE,
        DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US),
        DateTimeFormatter.ofPattern("d/M/yyyy", Locale.US)
    )
    val parsed = formatters.firstNotNullOfOrNull { formatter ->
        try {
            LocalDate.parse(value, formatter)
        } catch (_: DateTimeParseException) {
            null
        }
    } ?: return false
    return !parsed.isAfter(LocalDate.now())
}
