package com.danielomeara.buildabudget.features.bottomnavigation.budget.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.danielomeara.buildabudget.R
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.relations.BudgetCategoryWithBudgetItems
import com.danielomeara.buildabudget.features.bottomnavigation.budget.presentation.fragments.BudgetFragmentDirections

class BudgetCategoryAdapter(private val dataSet: List<BudgetCategoryWithBudgetItems>, private val budgetId: Long) :
    RecyclerView.Adapter<BudgetCategoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.cardView)
        val title: TextView = view.findViewById(R.id.textViewCategoryTitle)
        val addItem: TextView = view.findViewById(R.id.textViewAddItem)
        val recyclerViewBudgetItems: RecyclerView = view.findViewById(R.id.recyclerViewBudgetItems)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_item_category, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.cardView.setOnLongClickListener {
            val action = BudgetFragmentDirections.actionBudgetFragmentToAddEditBudgetCategoryFragment(budgetId).apply {
                budgetCategory = dataSet[position].budgetCategory
            }
            it.findNavController().navigate(action)
            true
        }

        viewHolder.recyclerViewBudgetItems.adapter = BudgetItemAdapter(dataSet[position].budgetItems, budgetId)
        viewHolder.title.text = dataSet[position].budgetCategory.categoryName

        viewHolder.addItem.setOnClickListener {
            it.findNavController().navigate(
                BudgetFragmentDirections.actionBudgetFragmentToAddBudgetItemFragment(
                    dataSet[position].budgetCategory.categoryId,
                    null,
                    budgetId
                )
            )
        }
    }

    override fun getItemCount() = dataSet.size

}