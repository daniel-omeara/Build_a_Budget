package com.danielomeara.buildabudget.features.login.domain.usecases

import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.Budget
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.repository.BudgetRepository
import com.danielomeara.buildabudget.features.login.domain.models.User
import com.danielomeara.buildabudget.features.login.domain.repository.UserRepository
import com.danielomeara.buildabudget.utils.InvalidUserException
import com.danielomeara.buildabudget.utils.security.encryptString

class AddUser(
    private val userRepository: UserRepository,
    private val budgetRepository: BudgetRepository
) {

    @Throws(InvalidUserException::class)
    suspend operator fun invoke(username: String, password: String, confirmPassword: String, budgetName: String) {
        if(username.isBlank()) {
            throw InvalidUserException("Username cannot be blank")
        }
        if(password.isBlank()) {
            throw InvalidUserException("Password cannot be blank")
        }
        if(password != confirmPassword) {
            throw InvalidUserException("Password and Confirm password must match")
        }
        if(budgetName.isBlank()) {
            throw InvalidUserException("Budget name cannot be blank")
        }
        val budgetId = budgetRepository.insertBudget(Budget(budgetName = budgetName))
        userRepository.insertUser(User(username = username, password = encryptString(password), budgetId = budgetId))
    }

}