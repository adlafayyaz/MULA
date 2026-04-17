package com.example.mula.data.model

data class UserProfile(
    val user_id: String,
    val username: String,
    val phone_number: String,
    val birth_date: String,
    val gender: String,
    val join_date: String,
    val profile_image_res_name: String?
)
