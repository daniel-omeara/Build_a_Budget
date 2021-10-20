package com.danielomeara.buildabudget.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danielomeara.buildabudget.features.bottomnavigation.budget.data.datasource.BudgetDao
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.Budget
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetCategory
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetItem
import com.danielomeara.buildabudget.features.login.data.datasource.UserDao
import com.danielomeara.buildabudget.features.login.domain.models.User

@Database(
    entities = [
        User::class,
        Budget::class,
        BudgetCategory::class,
        BudgetItem::class
   ],
    version = 1
)
abstract class BuildABudgetDatabase: RoomDatabase() {

    abstract val userDao: UserDao

    abstract val budgetDao: BudgetDao

    companion object {
        const val DATABASE_NAME = "build_a_budget_db"
    }

}