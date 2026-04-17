package com.example.mula.data.datasource

import com.example.mula.data.model.RewardItem

interface RewardsDataSource {
    suspend fun get_rewards(): Result<List<RewardItem>>
    suspend fun get_reward_filters(): Result<List<String>>
    suspend fun get_token_balance(): Result<Int>
    suspend fun redeem_reward(reward_id: String): Result<Int>
}

class FakeRewardsDataSource : RewardsDataSource {
    override suspend fun get_rewards(): Result<List<RewardItem>> = Result.success(
        listOf(
            RewardItem("reward_1", "free_latte", 120, "img_reward_latte", "coffee", true),
            RewardItem("reward_2", "brownies", 90, "img_reward_brownies", "snack", true)
        )
    )

    override suspend fun get_reward_filters(): Result<List<String>> = Result.success(listOf("all", "coffee", "snack"))

    override suspend fun get_token_balance(): Result<Int> = Result.success(240)

    override suspend fun redeem_reward(reward_id: String): Result<Int> = Result.success(120)
}
