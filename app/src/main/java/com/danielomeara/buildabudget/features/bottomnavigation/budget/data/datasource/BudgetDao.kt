package com.danielomeara.buildabudget.features.bottomnavigation.budget.data.datasource

import androidx.lifecycle.LiveData
import androidx.room.*
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.Budget
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetCategory
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetItem
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.relations.BudgetCategoryWithBudgetItems
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.relations.BudgetWithCategoriesAndItems
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: Budget): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudgetCategory(budgetCategory: BudgetCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudgetItem(budgetItem: BudgetItem)

    @Query("SELECT * FROM budget WHERE budgetId = :budgetId")
    suspend fun getBudgetById(budgetId: Long): Budget?

    @Query("SELECT * FROM budget WHERE budgetId = :budgetId")
    fun getBudgetByFlowById(budgetId: Long): Flow<Budget>

    @Delete
    suspend fun deleteBudgetItem(budgetItem: BudgetItem)

    @Delete
    suspend fun deleteBudgetCategory(budgetCategory: BudgetCategory)

    @Transaction
    @Query("SELECT * FROM budget WHERE budgetId = :budgetId")
    fun getBudgetWithCategoriesAndItems(budgetId: Long): LiveData<BudgetWithCategoriesAndItems>

}