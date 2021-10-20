package com.danielomeara.buildabudget.features.bottomnavigation.profile.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.Budget
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.repository.BudgetRepository
import com.danielomeara.buildabudget.features.login.domain.models.User
import com.danielomeara.buildabudget.features.login.domain.repository.UserRepository
import com.danielomeara.buildabudget.utils.LOGGED_IN_USER
import com.danielomeara.buildabudget.utils.keyvaluestore.KeyValueStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val keyValueStore: KeyValueStore,
    private val userRepository: UserRepository,
    private val budgetRepository: BudgetRepository,
    val user: User
): ViewModel() {

    val budget: LiveData<Budget> = budgetRepository.getBudgetByFlowById(user.budgetId).asLiveData()

    fun removeUser() {
        keyValueStore.remove(LOGGED_IN_USER)
    }

    fun deleteUser() {
        viewModelScope.launch {
            userRepository.deleteUser(user)
        }
    }

    fun changeBudgetName(budgetName: String) {
        viewModelScope.launch {
            budget.value?.let {
                budgetRepository.insertBudget(it.copy(budgetName = budgetName))
            }
        }
    }

}