package com.danielomeara.buildabudget.features.bottomnavigation.budget.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danielomeara.buildabudget.R
import com.danielomeara.buildabudget.databinding.FragmentAddEditBudgetCategoryBinding
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.models.BudgetCategory
import com.danielomeara.buildabudget.features.bottomnavigation.budget.presentation.viewmodels.AddEditBudgetCategoryViewModel
import com.danielomeara.buildabudget.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditBudgetCategoryFragment : BaseFragment<FragmentAddEditBudgetCategoryBinding, AddEditBudgetCategoryViewModel>() {

    override val viewModel: AddEditBudgetCategoryViewModel by viewModels()

    private val args: AddEditBudgetCategoryFragmentArgs by navArgs()

    override fun getFragmentView(): Int = R.layout.fragment_add_edit_budget_category

    override fun getLifecycleOwner(): LifecycleOwner = this@AddEditBudgetCategoryFragment

    override fun setUpListeners() {
        getViewBinding().apply {
            imageButtonDeleteItem.setOnClickListener {
                args.budgetCategory?.let { budgetCategory -> viewModel.deleteCategory(budgetCategory) }
                findNavController().popBackStack()
            }
            toolbarAddCategory.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            toolbarAddCategory.toolbar.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.itemAdd -> {
                        viewModel.addCategory(
                            args.budgetCategory?.copy(categoryName = editTextCategoryName.text.toString()) ?: BudgetCategory(categoryName =
                            editTextCategoryName
                                .text.toString(),
                                budgetId = args
                                .budgetId))
                        findNavController().navigate(AddEditBudgetCategoryFragmentDirections.actionAddEditBudgetCategoryFragmentToBudgetFragment())
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewBinding().apply {
            toolbarAddCategory.toolbar.inflateMenu(R.menu.menu_add_item)
            toolbarAddCategory.toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_arrow_back_24)

            args.budgetCategory?.let {
                editTextCategoryName.setText(it.categoryName)

                imageButtonDeleteItem.visibility = View.VISIBLE
            }
        }
    }
}