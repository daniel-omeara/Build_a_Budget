package com.danielomeara.buildabudget.features.bottomnavigation.profile.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.danielomeara.buildabudget.R
import com.danielomeara.buildabudget.databinding.FragmentProfileBinding
import com.danielomeara.buildabudget.features.bottomnavigation.profile.presentation.viewmodels.ProfileViewModel
import com.danielomeara.buildabudget.features.login.presentation.LoginActivity
import com.danielomeara.buildabudget.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()

    override fun getFragmentView(): Int = R.layout.fragment_profile

    override fun setUpListeners() {
        getViewBinding().apply {
            toolbarProfile.toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.itemLogout -> {
                        viewModel.removeUser()
                        startActivity(Intent(requireContext(), LoginActivity::class.java))
                        true
                    }
                    else -> false
                }
            }
            editTextBudgetName.apply {
                setOnFocusChangeListener { _, focused ->
                    if(!focused) {
                        viewModel.changeBudgetName(editTextBudgetName.text.toString())
                    }
                }
                setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        getListener()?.requestHideKeyboard()
                        clearFocus()
                        return@OnKeyListener true
                    }
                    false
                })
            }
        }

        viewModel.budget.observe(getLifecycleOwner(), {
            it?.let {
                getViewBinding().editTextBudgetName.setText(it.budgetName)
            }
        })
    }

    override fun getLifecycleOwner(): LifecycleOwner = this@ProfileFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewBinding().apply {
            toolbarProfile.toolbar.title = viewModel.user.username
            toolbarProfile.toolbar.inflateMenu(R.menu.menu_profile)
        }
    }
}