package com.example.mula.data.repository

import com.example.mula.data.model.RewardItem

interface RewardsRepository {
    suspend fun get_rewards(): Result<List<RewardItem>>
    suspend fun get_reward_filters(): Result<List<String>>
    suspend fun get_token_balance(): Result<Int>
    suspend fun redeem_reward(reward_id: String): Result<Int>
}
