package com.danielomeara.buildabudget.features.bottomnavigation.budgetsummary.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.relations.BudgetWithCategoriesAndItems
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.repository.BudgetRepository
import com.danielomeara.buildabudget.features.login.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetSummaryViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
    user: User
): ViewModel() {

    val budget: LiveData<BudgetWithCategoriesAndItems> = budgetRepository.getBudgetWithCategoriesAndItems(user.budgetId)

    fun updateBudget(expectedMonthlyIncome: Double) {
        viewModelScope.launch {
            budget.value?.budget?.let { budgetRepository.insertBudget(it.copy(expectedMonthlyIncome = expectedMonthlyIncome)) }
        }
    }

}