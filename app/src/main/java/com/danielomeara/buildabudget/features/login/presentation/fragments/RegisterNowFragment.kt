package com.danielomeara.buildabudget.features.login.presentation.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.danielomeara.buildabudget.R
import com.danielomeara.buildabudget.databinding.FragmentRegisterNowBinding
import com.danielomeara.buildabudget.features.login.presentation.viewmodels.RegisterNowViewModel
import com.danielomeara.buildabudget.utils.base.BaseFragment
import com.danielomeara.buildabudget.utils.InvalidUserException
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterNowFragment: BaseFragment<FragmentRegisterNowBinding, RegisterNowViewModel>() {
    override fun getFragmentView(): Int = R.layout.fragment_register_now

    override fun getLifecycleOwner(): LifecycleOwner = this@RegisterNowFragment

    override val viewModel: RegisterNowViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewBinding().apply {
            toolbarRegisterNow.toolbar.title = getString(R.string.register_now)
            toolbarRegisterNow.toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_arrow_back_24)
        }

    }

    override fun setUpListeners() {
        getViewBinding().apply {
            toolbarRegisterNow.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            buttonSignUp.setOnClickListener {
                onRegisterClick()
            }
            textInputEditTextBudgetName.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    getListener()?.requestHideKeyboard()
                    return@OnKeyListener true
                }
                false
            })
        }
    }


    private fun onRegisterClick() {
        val username = getViewBinding().textInputEditTextUsername.text.toString()
        val password = getViewBinding().textInputEditTextPassword.text.toString()
        val confirmPassword = getViewBinding().textInputEditTextConfirmPassword.text.toString()
        val budgetName = getViewBinding().textInputEditTextBudgetName.text.toString()
        getLifecycleOwner().lifecycleScope.launch {
            try {
                viewModel.registerUser(username, password, confirmPassword, budgetName)
                val action = RegisterNowFragmentDirections.actionRegisterNowFragmentToLoginFragment().apply {
                    this.username = username
                    this.password = password
                }
                findNavController().navigate(action)
            } catch (invalidUserException: InvalidUserException) {
                invalidUserException.message?.let {
                    Snackbar.make(getViewBinding().root, it, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

}