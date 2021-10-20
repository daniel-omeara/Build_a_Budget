package com.danielomeara.buildabudget.features.bottomnavigation.budget.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetItem
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetItemType
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditBudgetItemViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository
): ViewModel() {

    private val _budgetItemType = MutableLiveData(BudgetItemType.INCOME)
    val budgetItemType: LiveData<BudgetItemType> = _budgetItemType

    fun addItem(budgetItem: BudgetItem, budgetId: Long) {
        viewModelScope.launch {
            budgetRepository.insertBudgetItem(budgetItem)
            val budget = budgetRepository.getBudgetById(budgetId)
            when(budgetItem.itemType) {
                BudgetItemType.INCOME -> {
                    if(budget?.currentMonthlyIncome != budgetItem.value) {
                        budget?.copy(currentMonthlyIncome = budget.currentMonthlyIncome + budgetItem.value)?.let { budgetRepository.insertBudget(it) }
                    }
                }
                BudgetItemType.EXPENSE -> {
                    budget?.copy(currentMonthlySpent = budget.currentMonthlySpent + budgetItem.value)?.let { budgetRepository.insertBudget(it) }
                }
            }
        }
    }

    fun subtractFromBudget(budgetItem: BudgetItem, budgetId: Long) {
        viewModelScope.launch {
            val budget = budgetRepository.getBudgetById(budgetId)
            when(budgetItem.itemType) {
                BudgetItemType.INCOME -> {
                    budget?.copy(currentMonthlyIncome = budget.currentMonthlyIncome.minus(budgetItem.value))?.let { budgetRepository.insertBudget(it) }
                }
                BudgetItemType.EXPENSE -> {
                    budget?.copy(currentMonthlySpent = budget.currentMonthlySpent.minus(budgetItem.value))?.let { budgetRepository.insertBudget(it) }
                }
            }
        }
    }

    fun setBudgetItemType(itemType: BudgetItemType) {
        _budgetItemType.value = itemType
    }

    fun deleteItem(budgetItem: BudgetItem) {
        viewModelScope.launch {
            budgetRepository.deleteBudgetItem(budgetItem)
        }
    }

}