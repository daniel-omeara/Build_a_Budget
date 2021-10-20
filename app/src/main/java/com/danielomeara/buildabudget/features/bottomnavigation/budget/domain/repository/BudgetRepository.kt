package com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.repository

import androidx.lifecycle.LiveData
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.Budget
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetCategory
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetItem
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.relations.BudgetWithCategoriesAndItems
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {

    suspend fun insertBudget(budget: Budget): Long

    suspend fun insertBudgetCategory(budgetCategory: BudgetCategory)

    suspend fun insertBudgetItem(budgetItem: BudgetItem)

    suspend fun getBudgetById(budgetId: Long): Budget?

    fun getBudgetByFlowById(budgetId: Long): Flow<Budget>

    suspend fun deleteBudgetItem(budgetItem: BudgetItem)

    suspend fun deleteBudgetCategory(budgetCategory: BudgetCategory)

    fun getBudgetWithCategoriesAndItems(budgetId: Long): LiveData<BudgetWithCategoriesAndItems>
}