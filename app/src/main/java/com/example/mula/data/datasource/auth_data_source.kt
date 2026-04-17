package com.example.mula.data.datasource

import com.example.mula.data.model.User

interface AuthDataSource {
    suspend fun login(username: String, password: String): Result<User>
    suspend fun register(username: String, phone_number: String, gender: String, birth_date: String, password: String): Result<User>
    suspend fun request_password_reset(phone_number: String): Result<Unit>
    suspend fun verify_otp(phone_number: String, otp_code: String): Result<String>
    suspend fun reset_password(reset_token: String, new_password: String): Result<Unit>
    suspend fun logout(): Result<Unit>
}

class FakeAuthDataSource : AuthDataSource {
    override suspend fun login(username: String, password: String): Result<User> =
        Result.success(User(id = "user_1", username = username, phone_number = "081234567890"))

    override suspend fun register(username: String, phone_number: String, gender: String, birth_date: String, password: String): Result<User> =
        Result.success(User(id = "user_1", username = username, phone_number = phone_number))

    override suspend fun request_password_reset(phone_number: String): Result<Unit> = Result.success(Unit)

    override suspend fun verify_otp(phone_number: String, otp_code: String): Result<String> = Result.success("reset_token_1")

    override suspend fun reset_password(reset_token: String, new_password: String): Result<Unit> = Result.success(Unit)

    override suspend fun logout(): Result<Unit> = Result.success(Unit)
}
