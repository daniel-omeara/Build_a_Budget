package com.danielomeara.buildabudget.features.login.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.danielomeara.buildabudget.features.login.domain.usecases.AddUser
import com.danielomeara.buildabudget.utils.InvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterNowViewModel @Inject constructor(
    private val addUser: AddUser
): ViewModel() {

    @Throws(InvalidUserException::class)
    suspend fun registerUser(username: String, password: String, confirmPassword: String, budgetName: String) {
        try {
            addUser(username, password, confirmPassword, budgetName)
        } catch(invalidUserException: InvalidUserException) {
            throw invalidUserException
        }
    }
}