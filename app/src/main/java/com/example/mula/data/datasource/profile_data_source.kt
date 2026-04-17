package com.example.mula.data.datasource

import com.example.mula.data.model.UserProfile

interface ProfileDataSource {
    suspend fun get_profile(): Result<UserProfile>
    suspend fun update_username(username: String): Result<UserProfile>
    suspend fun update_phone_number(phone_number: String): Result<UserProfile>
}

class FakeProfileDataSource : ProfileDataSource {
    private val profile = UserProfile("user_1", "mula_user", "081234567890", "1998-01-01", "female", "2025-09-01", null)

    override suspend fun get_profile(): Result<UserProfile> = Result.success(profile)

    override suspend fun update_username(username: String): Result<UserProfile> = Result.success(profile.copy(username = username))

    override suspend fun update_phone_number(phone_number: String): Result<UserProfile> = Result.success(profile.copy(phone_number = phone_number))
}
