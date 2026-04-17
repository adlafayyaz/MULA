package com.example.mula.data.datasource

import com.example.mula.data.model.AppSession

interface SessionDataSource {
    suspend fun get_session(): AppSession
    suspend fun save_session(session: AppSession)
    suspend fun clear_session()
    suspend fun set_first_launch_completed()
    suspend fun is_first_launch(): Boolean
}

class FakeSessionDataSource : SessionDataSource {
    private var session = AppSession(
        token = "",
        refresh_token = null,
        user_id = "",
        is_logged_in = false,
        is_first_launch = true
    )

    override suspend fun get_session(): AppSession = session

    override suspend fun save_session(session: AppSession) {
        this.session = session
    }

    override suspend fun clear_session() {
        session = session.copy(token = "", refresh_token = null, user_id = "", is_logged_in = false)
    }

    override suspend fun set_first_launch_completed() {
        session = session.copy(is_first_launch = false)
    }

    override suspend fun is_first_launch(): Boolean = session.is_first_launch
}
