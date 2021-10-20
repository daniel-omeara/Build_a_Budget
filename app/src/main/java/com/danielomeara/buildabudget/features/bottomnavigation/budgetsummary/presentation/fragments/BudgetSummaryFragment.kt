package com.danielomeara.buildabudget.features.bottomnavigation.budgetsummary.presentation.fragments

import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.danielomeara.buildabudget.R
import com.danielomeara.buildabudget.databinding.FragmentBudgetSummaryBinding
import com.danielomeara.buildabudget.features.bottomnavigation.budgetsummary.presentation.viewmodels.BudgetSummaryViewModel
import com.danielomeara.buildabudget.utils.base.BaseFragment
import com.danielomeara.buildabudget.utils.formatDoubleAsCurrencyString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BudgetSummaryFragment: BaseFragment<FragmentBudgetSummaryBinding, BudgetSummaryViewModel>() {

    override val viewModel: BudgetSummaryViewModel by viewModels()

    override fun getFragmentView(): Int = R.layout.fragment_budget_summary

    override fun getLifecycleOwner(): LifecycleOwner = this@BudgetSummaryFragment

    override fun setUpListeners() {
        viewModel.budget.observe(getLifecycleOwner(), {
            it?.let {
                getViewBinding().apply {
                    textViewBudgetName.text = it.budget.budgetName
                    textViewCurrentMonthlyIncome.text = formatDoubleAsCurrencyString(it.budget.currentMonthlyIncome)
                    textViewCurrentMonthlySpent.text = formatDoubleAsCurrencyString(it.budget.currentMonthlySpent)
                    if(it.budget.expectedMonthlyIncome == 0.0) {
                        editTextEstimatedMonthlyIncome.hint = formatDoubleAsCurrencyString(it.budget.expectedMonthlyIncome)
                    } else {
                        editTextEstimatedMonthlyIncome.setText(formatDoubleAsCurrencyString(it.budget.expectedMonthlyIncome))
                    }

                    val balance = it.budget.currentMonthlyIncome.minus(it.budget.currentMonthlySpent)
                    textViewBalance.text = formatDoubleAsCurrencyString(balance)
                    if(balance > 0.0) {
                        textViewBalance.setTextColor(resources.getColor(android.R.color.holo_green_dark, resources.newTheme()))
                    } else {
                        textViewBalance.setTextColor(resources.getColor(android.R.color.holo_red_dark, resources.newTheme()))
                    }
                }
            }
        })

        getViewBinding().apply {
            editTextEstimatedMonthlyIncome.setOnFocusChangeListener { _, focused ->
                if(!focused) {
                    val temp = editTextEstimatedMonthlyIncome.text.toString().replace("""[$,]""".toRegex(), "").toDouble()
                    editTextEstimatedMonthlyIncome.setText(formatDoubleAsCurrencyString(temp))
                    viewModel.updateBudget(temp)
                }
            }

            editTextEstimatedMonthlyIncome.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    getListener()?.requestHideKeyboard()
                    editTextEstimatedMonthlyIncome.clearFocus()
                    return@OnKeyListener true
                }
                false
            })
        }
    }


}