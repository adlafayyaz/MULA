package com.example.mula.data.repository

import com.example.mula.data.model.User

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<User>
    suspend fun register(username: String, phone_number: String, gender: String, birth_date: String, password: String): Result<User>
    suspend fun request_password_reset(phone_number: String): Result<Unit>
    suspend fun verify_otp(phone_number: String, otp_code: String): Result<String>
    suspend fun reset_password(reset_token: String, new_password: String): Result<Unit>
    suspend fun logout(): Result<Unit>
}
