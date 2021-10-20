package com.danielomeara.buildabudget.utils.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.danielomeara.buildabudget.R
import com.danielomeara.buildabudget.utils.interfaces.KeyboardListener

abstract class BaseCompatActivity: AppCompatActivity(), KeyboardListener {

    abstract fun getActivityView(): Int

    abstract fun getNavGraph(): Int

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var bundle: Bundle

    fun getNavController() = navController
    fun getBundle() = bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BuildABudget)
        setContentView(getActivityView())

        navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController.apply {
            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(getNavGraph())
            this.graph = graph
        }

        intent?.extras?.let {
            bundle = it
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if(navHostFragment.childFragmentManager.backStackEntryCount == 0){
            onBackPressed()
        } else {
            navController.popBackStack()
        }
        return true
    }

    override fun onBackPressed() {
        if(navHostFragment.childFragmentManager.backStackEntryCount == 0){
            super.onBackPressed()
        } else {
            navController.popBackStack()
        }
    }

    private fun hideKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun requestHideKeyboard() {
        hideKeyboard()
    }

}