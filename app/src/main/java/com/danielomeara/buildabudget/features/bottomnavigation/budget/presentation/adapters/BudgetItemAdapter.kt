package com.danielomeara.buildabudget.features.bottomnavigation.budget.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.danielomeara.buildabudget.R
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetItem
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetItemType
import com.danielomeara.buildabudget.features.bottomnavigation.budget.presentation.fragments.BudgetFragmentDirections
import com.danielomeara.buildabudget.utils.formatDoubleAsCurrencyString

class BudgetItemAdapter(private val dataSet: List<BudgetItem>, private val budgetId: Long) :
    RecyclerView.Adapter<BudgetItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val constraintLayout: ConstraintLayout = view.findViewById(R.id.constraintLayout)
        val textViewBudgetItemName: TextView = view.findViewById(R.id.textViewBudgetItemName)
        val textViewBudgetItemValue: TextView = view.findViewById(R.id.textViewBudgetItemValue)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_item_budget_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.constraintLayout.setOnClickListener {
            it.findNavController().navigate(
                BudgetFragmentDirections.actionBudgetFragmentToAddBudgetItemFragment(
                    dataSet[position].categoryId,
                    dataSet[position],
                    budgetId
                )
            )
        }

        viewHolder.textViewBudgetItemName.text = dataSet[position].name
        viewHolder.textViewBudgetItemValue.text = formatDoubleAsCurrencyString(dataSet[position].value)

        when(dataSet[position].itemType) {
            BudgetItemType.INCOME -> {
                viewHolder.textViewBudgetItemValue.setTextColor(
                    viewHolder.itemView.resources.getColor(
                        android.R.color.holo_green_dark,
                        viewHolder.itemView.resources.newTheme()
                    )
                )
            }
            BudgetItemType.EXPENSE -> {
                viewHolder.textViewBudgetItemValue.setTextColor(
                    viewHolder.itemView.resources.getColor(
                        android.R.color.holo_red_dark,
                        viewHolder.itemView.resources.newTheme()
                    )
                )
            }
        }
    }

    override fun getItemCount() = dataSet.size

}