package com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class BudgetCategory(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Long = 0,
    val categoryName: String,
    val budgetId: Long
): Serializable
