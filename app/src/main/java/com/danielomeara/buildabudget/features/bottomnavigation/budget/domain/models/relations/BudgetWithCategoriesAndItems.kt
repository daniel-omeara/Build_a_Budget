package com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.Budget
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetCategory

data class BudgetWithCategoriesAndItems(
    @Embedded val budget: Budget,
    @Relation(
        entity = BudgetCategory::class,
        parentColumn = "budgetId",
        entityColumn = "budgetId"
    )
    val budgetCategories: List<BudgetCategoryWithBudgetItems>
)