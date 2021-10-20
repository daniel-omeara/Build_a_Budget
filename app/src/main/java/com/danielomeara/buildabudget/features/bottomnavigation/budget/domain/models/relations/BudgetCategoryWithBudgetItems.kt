package com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetCategory
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetItem

data class BudgetCategoryWithBudgetItems(
    @Embedded val budgetCategory: BudgetCategory,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId",
    )
    val budgetItems: List<BudgetItem>
)