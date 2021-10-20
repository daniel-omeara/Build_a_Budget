package com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Budget(
    @PrimaryKey(autoGenerate = true)
    val budgetId: Long = 0,
    val budgetName: String,
    val currentMonthlyIncome: Double = 0.0,
    val currentMonthlySpent: Double = 0.0,
    val expectedMonthlyIncome: Double = 0.0
)
