package com.danielomeara.buildabudget.features.login.data.repository

import com.danielomeara.buildabudget.features.login.data.datasource.UserDao
import com.danielomeara.buildabudget.features.login.domain.models.User
import com.danielomeara.buildabudget.features.login.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao,
): UserRepository {

    override suspend fun getUserByUsernamePassword(username: String, password: String): User? {
        return userDao.getUserByUsernamePassword(username, password)
    }

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
}