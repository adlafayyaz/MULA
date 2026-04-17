package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mula.data.model.RewardItem
import com.example.mula.data.repository.RewardsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

data class RewardsScreenState(
    val is_loading: Boolean = false,
    val error_message: String? = null,
    val token_balance_text: String = "",
    val filter_labels: List<String> = emptyList(),
    val selected_filter: String? = null,
    val rewards: List<RewardItem> = emptyList(),
    val filtered_rewards: List<RewardItem> = emptyList(),
    val redeeming_reward_id: String? = null,
    val navigation_target: String? = null,
    val pop_back_stack: Boolean = false
)

sealed class RewardsScreenEvent {
    data object OnScreenOpened : RewardsScreenEvent()
    data object OnBackClicked : RewardsScreenEvent()
    data class OnFilterSelected(val filter: String) : RewardsScreenEvent()
    data class OnRewardClicked(val reward_id: String) : RewardsScreenEvent()
    data object OnRetryClicked : RewardsScreenEvent()
    data object OnNavigationHandled : RewardsScreenEvent()
    data object OnErrorMessageDismissed : RewardsScreenEvent()
}

class RewardsViewModel : ViewModel() {
    private val rewards_repository: RewardsRepository = Stage5ARepositoryProvider.rewards_repository

    var state by mutableStateOf(RewardsScreenState())
        private set

    fun on_event(event: RewardsScreenEvent) {
        when (event) {
            RewardsScreenEvent.OnScreenOpened,
            RewardsScreenEvent.OnRetryClicked -> load_rewards()
            RewardsScreenEvent.OnBackClicked -> state = state.copy(pop_back_stack = true)
            is RewardsScreenEvent.OnFilterSelected -> apply_filter(event.filter)
            is RewardsScreenEvent.OnRewardClicked -> redeem_reward(event.reward_id)
            RewardsScreenEvent.OnNavigationHandled -> state = state.copy(pop_back_stack = false, navigation_target = null)
            RewardsScreenEvent.OnErrorMessageDismissed -> state = state.copy(error_message = null)
        }
    }

    private fun load_rewards() {
        if (state.is_loading) return
        viewModelScope.launch {
            state = state.copy(is_loading = true, error_message = null)
            runCatching {
                val token = async { rewards_repository.get_token_balance().getOrThrow() }
                val filters = async { rewards_repository.get_reward_filters().getOrThrow() }
                val rewards = async { rewards_repository.get_rewards().getOrThrow() }
                val reward_list = rewards.await()
                val filter_list = filters.await()
                val selected_filter = state.selected_filter ?: filter_list.firstOrNull()
                state = state.copy(
                    is_loading = false,
                    token_balance_text = build_token_balance_text(token.await()),
                    filter_labels = filter_list,
                    selected_filter = selected_filter,
                    rewards = reward_list,
                    filtered_rewards = filter_rewards(
                        rewards = reward_list,
                        selected_filter = selected_filter
                    )
                )
            }.onFailure { throwable ->
                state = state.copy(
                    is_loading = false,
                    error_message = throwable.message ?: "Gagal memuat rewards"
                )
            }
        }
    }

    private fun apply_filter(filter: String) {
        state = state.copy(
            selected_filter = filter,
            filtered_rewards = filter_rewards(
                rewards = state.rewards,
                selected_filter = filter
            )
        )
    }

    private fun redeem_reward(reward_id: String) {
        if (state.is_loading || state.redeeming_reward_id != null) return
        val reward = state.rewards.firstOrNull { it.id == reward_id } ?: return
        if (!reward.is_redeemable) {
            state = state.copy(error_message = "Reward belum bisa ditukar")
            return
        }
        viewModelScope.launch {
            state = state.copy(redeeming_reward_id = reward_id, error_message = null)
            rewards_repository.redeem_reward(reward_id = reward_id)
                .onSuccess { remaining_balance ->
                    val updated_rewards = state.rewards.map {
                        if (it.id == reward_id) it.copy(is_redeemable = false) else it
                    }
                    state = state.copy(
                        redeeming_reward_id = null,
                        token_balance_text = build_token_balance_text(remaining_balance),
                        rewards = updated_rewards,
                        filtered_rewards = filter_rewards(
                            rewards = updated_rewards,
                            selected_filter = state.selected_filter
                        )
                    )
                }
                .onFailure { throwable ->
                    state = state.copy(
                        redeeming_reward_id = null,
                        error_message = throwable.message ?: "Redeem reward gagal"
                    )
                }
        }
    }

    private fun filter_rewards(rewards: List<RewardItem>, selected_filter: String?): List<RewardItem> =
        if (selected_filter.isNullOrBlank() || selected_filter == "all") rewards
        else rewards.filter { it.filter_id == selected_filter }
}
