package com.danielomeara.buildabudget.features.bottomnavigation.budget.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danielomeara.buildabudget.R
import com.danielomeara.buildabudget.databinding.FragmentAddEditBudgetItemBinding
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetItem
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetItemType
import com.danielomeara.buildabudget.features.bottomnavigation.budget.presentation.viewmodels.AddEditBudgetItemViewModel
import com.danielomeara.buildabudget.utils.base.BaseFragment
import com.danielomeara.buildabudget.utils.formatDoubleAsCurrencyString
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditBudgetItemFragment: BaseFragment<FragmentAddEditBudgetItemBinding, AddEditBudgetItemViewModel>() {
    override val viewModel: AddEditBudgetItemViewModel by viewModels()
    private val args: AddEditBudgetItemFragmentArgs by navArgs()

    override fun getFragmentView(): Int = R.layout.fragment_add_edit_budget_item

    override fun getLifecycleOwner(): LifecycleOwner = this@AddEditBudgetItemFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewBinding().apply {
            toolbarAddItem.toolbar.inflateMenu(R.menu.menu_add_item)
            toolbarAddItem.toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_arrow_back_24)

            args.budgetItem?.let {
                editTextItemLabel.setText(it.name)
                editTextItemValue.setText(formatDoubleAsCurrencyString(it.value))
                when(it.itemType) {
                    BudgetItemType.INCOME -> {
                        toggleButton.clearChecked()
                        toggleButton.check(R.id.toggleButtonIncome)
                    }
                    BudgetItemType.EXPENSE -> {
                        toggleButton.clearChecked()
                        toggleButton.check(R.id.toggleButtonExpense)
                    }
                }

                imageButtonDeleteItem.visibility = View.VISIBLE

                viewModel.subtractFromBudget(it, args.budgetId)
            }
        }
    }

    override fun setUpListeners() {
        getViewBinding().apply {
            imageButtonDeleteItem.setOnClickListener {
                args.budgetItem?.let { budgetItem -> viewModel.deleteItem(budgetItem) }
                findNavController().popBackStack()
            }
            editTextItemValue.setOnFocusChangeListener { _, focused ->
                if(!focused) {
                    val temp = editTextItemValue.text.toString().replace("""[$,]""".toRegex(), "").toDouble()
                    editTextItemValue.setText(formatDoubleAsCurrencyString(temp))
                }
            }
            toggleButton.addOnButtonCheckedListener { _, checkedId, _ ->
                when(checkedId) {
                    R.id.toggleButtonIncome -> {
                        viewModel.setBudgetItemType(BudgetItemType.INCOME)
                    }
                    R.id.toggleButtonExpense -> {
                        viewModel.setBudgetItemType(BudgetItemType.EXPENSE)
                    }
                }
            }
            toolbarAddItem.toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.itemAdd -> {
                        getViewBinding().apply {
                            if(editTextItemLabel.text.isNotBlank() && editTextItemValue.text.isNotBlank() && viewModel.budgetItemType.value != null) {
                                if(args.budgetItem != null) {
                                    viewModel.addItem(
                                        args.budgetItem!!.copy(
                                            name = editTextItemLabel.text.toString(),
                                            value = editTextItemValue.text.toString().replace("""[$,]""".toRegex(), "").toDouble(),
                                            itemType = viewModel.budgetItemType.value!!
                                        ),
                                        args.budgetId
                                    )
                                } else {
                                    viewModel.addItem(
                                        BudgetItem(
                                            name = editTextItemLabel.text.toString(),
                                            value = editTextItemValue.text.toString().replace("""[$,]""".toRegex(), "").toDouble(),
                                            itemType = viewModel.budgetItemType.value!!,
                                            categoryId = args.categoryId
                                        ),
                                        args.budgetId
                                    )
                                }
                                findNavController().navigate(AddEditBudgetItemFragmentDirections.actionAddBudgetItemFragmentToBudgetFragment())
                            } else {
                                Snackbar.make(getViewBinding().root, "Unable to add the item.", Snackbar.LENGTH_LONG)
                                    .show()
                            }
                        }
                        true
                    }
                    else -> false
                }
            }
            toolbarAddItem.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}