package com.danielomeara.buildabudget.features.login.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danielomeara.buildabudget.features.login.domain.models.User
import com.danielomeara.buildabudget.features.login.domain.usecases.GetUser
import com.danielomeara.buildabudget.utils.InvalidUserException
import com.danielomeara.buildabudget.utils.LOGGED_IN_USER
import com.danielomeara.buildabudget.utils.keyvaluestore.KeyValueStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUser: GetUser,
    private val keyValueStore: KeyValueStore
): ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    @Throws(InvalidUserException::class)
    suspend fun loginUser(username: String, password: String) {
        _user.value = try {
            getUser(username, password) ?: throw InvalidUserException("User not found in database.")
        } catch (invalidUserException: InvalidUserException) {
            throw invalidUserException
        }
    }

    fun saveUser(user: User) {
        keyValueStore.putSerializable(LOGGED_IN_USER, user)
    }

}