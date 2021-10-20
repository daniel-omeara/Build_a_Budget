package com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class BudgetItem(
    @PrimaryKey(autoGenerate = true)
    val itemId: Long = 0,
    val name: String,
    val value: Double,
    val itemType: BudgetItemType,
    val categoryId: Long
): Serializable


enum class BudgetItemType {
    INCOME, EXPENSE
}