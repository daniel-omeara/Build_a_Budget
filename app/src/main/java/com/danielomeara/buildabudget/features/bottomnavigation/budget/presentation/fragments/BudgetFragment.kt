package com.danielomeara.buildabudget.features.bottomnavigation.budget.presentation.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.danielomeara.buildabudget.R
import com.danielomeara.buildabudget.databinding.FragmentBudgetBinding
import com.danielomeara.buildabudget.features.bottomnavigation.budget.presentation.adapters.BudgetCategoryAdapter
import com.danielomeara.buildabudget.features.bottomnavigation.budget.presentation.viewmodels.BudgetViewModel
import com.danielomeara.buildabudget.utils.base.BaseFragment
import com.danielomeara.buildabudget.utils.formatDoubleAsCurrencyString
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.floor

@AndroidEntryPoint
class BudgetFragment: BaseFragment<FragmentBudgetBinding, BudgetViewModel>() {

    override val viewModel: BudgetViewModel by viewModels()

    override fun getFragmentView(): Int = R.layout.fragment_budget

    override fun setUpListeners() {
        viewModel.budget.observe(getLifecycleOwner(), {
            it?.let {
                getViewBinding().apply {
                    recyclerViewBudgetCategories.adapter = BudgetCategoryAdapter(it.budgetCategories, it.budget.budgetId)

                    progressBar.secondaryProgress = floor(
                        (it.budget.currentMonthlyIncome / it.budget.expectedMonthlyIncome)
                                * 100).toInt()
                    if(it.budget.currentMonthlyIncome == 0.0) {
                        textViewIncome.text = formatDoubleAsCurrencyString(0.0)
                    } else {
                        textViewIncome.text = formatDoubleAsCurrencyString(it.budget.currentMonthlyIncome)
                    }

                    progressBar.progress = floor((it.budget.currentMonthlySpent / it.budget
                        .currentMonthlyIncome) *
                            100).toInt()
                    if(it.budget.currentMonthlySpent == 0.0) {
                        textViewIncome.text = formatDoubleAsCurrencyString(0.0)
                    } else {
                        textViewIncome.text = formatDoubleAsCurrencyString(it.budget.currentMonthlySpent)
                    }
                }
            }
        })
        getViewBinding().apply {
            buttonAddCategory.setOnClickListener {
                viewModel.budget.value?.budget?.let {
                    findNavController().navigate(BudgetFragmentDirections.actionBudgetFragmentToAddEditBudgetCategoryFragment(it.budgetId))
                }
            }
        }
    }

    override fun getLifecycleOwner(): LifecycleOwner = this@BudgetFragment

}