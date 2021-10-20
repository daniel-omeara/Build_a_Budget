package com.danielomeara.buildabudget.features.login.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danielomeara.buildabudget.R
import com.danielomeara.buildabudget.databinding.FragmentLoginBinding
import com.danielomeara.buildabudget.features.bottomnavigation.MainActivity
import com.danielomeara.buildabudget.features.login.presentation.viewmodels.LoginViewModel
import com.danielomeara.buildabudget.utils.base.BaseFragment
import com.danielomeara.buildabudget.utils.InvalidUserException
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override fun getFragmentView(): Int = R.layout.fragment_login

    private val args: LoginFragmentArgs by navArgs()
    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewBinding().textInputEditTextUsername.setText(args.username)
        getViewBinding().textInputEditTextPassword.setText(args.password)
    }

    override fun setUpListeners() {
        getViewBinding().textViewRegisterNow.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterNowFragment())
        }

        getViewBinding().buttonLogin.setOnClickListener {
            getLifecycleOwner().lifecycleScope.launch {
                try {
                    val username = getViewBinding().textInputEditTextUsername.text.toString()
                    val password = getViewBinding().textInputEditTextPassword.text.toString()
                    viewModel.loginUser(
                        username,
                        password
                    )
                } catch(invalidUserException: InvalidUserException) {
                    invalidUserException.message?.let {
                        Snackbar.make(getViewBinding().root, it, Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
        getViewBinding().textInputEditTextPassword.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                getListener()?.requestHideKeyboard()
                return@OnKeyListener true
            }
            false
        })
        viewModel.user.observe(getLifecycleOwner(), {
            if(it != null) {
                viewModel.saveUser(it)
                startActivity(
                    Intent(requireContext(), MainActivity::class.java)
                )
            }
        })
    }

    override fun getLifecycleOwner(): LifecycleOwner = this@LoginFragment

}