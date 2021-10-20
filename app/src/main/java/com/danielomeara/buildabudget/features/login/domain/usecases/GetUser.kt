package com.danielomeara.buildabudget.features.login.domain.usecases

import com.danielomeara.buildabudget.features.login.domain.models.User
import com.danielomeara.buildabudget.features.login.domain.repository.UserRepository
import com.danielomeara.buildabudget.utils.InvalidUserException
import com.danielomeara.buildabudget.utils.security.encryptString

class GetUser(
    private val repository: UserRepository
) {

    @Throws(InvalidUserException::class)
    suspend operator fun invoke(username: String, password: String): User? {
        if(username.isBlank()) {
            throw InvalidUserException("Username cannot be blank")
        }
        if(password.isBlank()) {
            throw InvalidUserException("Password cannot be blank")
        }
        return repository.getUserByUsernamePassword(username, encryptString(password))
    }
}