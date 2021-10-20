package com.danielomeara.buildabudget.features.bottomnavigation.budget.data.repository

import androidx.lifecycle.LiveData
import com.danielomeara.buildabudget.features.bottomnavigation.budget.data.datasource.BudgetDao
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.Budget
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetCategory
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetItem
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.relations.BudgetWithCategoriesAndItems
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow

class BudgetRepositoryImpl(
    private val budgetDao: BudgetDao
): BudgetRepository {
    override suspend fun insertBudget(budget: Budget): Long {
        return budgetDao.insertBudget(budget)
    }

    override suspend fun insertBudgetCategory(budgetCategory: BudgetCategory) {
        budgetDao.insertBudgetCategory(budgetCategory)
    }

    override suspend fun insertBudgetItem(budgetItem: BudgetItem) {
        budgetDao.insertBudgetItem(budgetItem)
    }

    override suspend fun getBudgetById(budgetId: Long): Budget? {
        return budgetDao.getBudgetById(budgetId)
    }

    override fun getBudgetByFlowById(budgetId: Long): Flow<Budget> {
        return budgetDao.getBudgetByFlowById(budgetId)
    }

    override suspend fun deleteBudgetItem(budgetItem: BudgetItem) {
        budgetDao.deleteBudgetItem(budgetItem)
    }

    override suspend fun deleteBudgetCategory(budgetCategory: BudgetCategory) {
        budgetDao.deleteBudgetCategory(budgetCategory)
    }

    override fun getBudgetWithCategoriesAndItems(budgetId: Long): LiveData<BudgetWithCategoriesAndItems> {
        return budgetDao.getBudgetWithCategoriesAndItems(budgetId)
    }
}