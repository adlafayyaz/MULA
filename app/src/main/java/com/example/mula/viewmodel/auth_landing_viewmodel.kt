package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.navigation.ROUTE_LOGIN
import com.example.mula.navigation.ROUTE_REGISTER

data class AuthLandingScreenState(
    val is_loading: Boolean = false,
    val error_message: String? = null,
    val navigation_target: String? = null
)

sealed class AuthLandingScreenEvent {
    data object OnLoginClicked : AuthLandingScreenEvent()
    data object OnRegisterClicked : AuthLandingScreenEvent()
    data object OnNavigationHandled : AuthLandingScreenEvent()
}

class AuthLandingViewModel : ViewModel() {
    var state by mutableStateOf(AuthLandingScreenState())
        private set

    fun on_event(event: AuthLandingScreenEvent) {
        state = when (event) {
            AuthLandingScreenEvent.OnLoginClicked -> state.copy(navigation_target = ROUTE_LOGIN)
            AuthLandingScreenEvent.OnRegisterClicked -> state.copy(navigation_target = ROUTE_REGISTER)
            AuthLandingScreenEvent.OnNavigationHandled -> state.copy(navigation_target = null)
        }
    }
}
