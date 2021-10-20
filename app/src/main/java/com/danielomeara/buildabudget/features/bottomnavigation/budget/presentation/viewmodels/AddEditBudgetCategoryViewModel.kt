package com.danielomeara.buildabudget.features.bottomnavigation.budget.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetCategory
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetItem
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditBudgetCategoryViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository
): ViewModel() {

    fun addCategory(budgetCategory: BudgetCategory) {
        viewModelScope.launch {
            budgetRepository.insertBudgetCategory(budgetCategory)
        }
    }

    fun deleteCategory(budgetCategory: BudgetCategory) {
        viewModelScope.launch {
            budgetRepository.deleteBudgetCategory(budgetCategory)
        }
    }

}