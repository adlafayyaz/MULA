package com.example.mula.data.repository

import com.example.mula.data.model.AppSession

interface SessionRepository {
    suspend fun get_session(): AppSession
    suspend fun save_session(session: AppSession)
    suspend fun clear_session()
    suspend fun set_first_launch_completed()
    suspend fun is_first_launch(): Boolean
}
