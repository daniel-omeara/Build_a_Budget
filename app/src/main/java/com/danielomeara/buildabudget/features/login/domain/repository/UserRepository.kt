package com.danielomeara.buildabudget.features.login.domain.repository

import com.danielomeara.buildabudget.features.login.domain.models.User

interface UserRepository {

    suspend fun getUserByUsernamePassword(username: String, password: String): User?

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)
}