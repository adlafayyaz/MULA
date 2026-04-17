package com.example.mula.data.repository

import com.example.mula.data.model.UserProfile

interface ProfileRepository {
    suspend fun get_profile(): Result<UserProfile>
    suspend fun update_username(username: String): Result<UserProfile>
    suspend fun update_phone_number(phone_number: String): Result<UserProfile>
}
