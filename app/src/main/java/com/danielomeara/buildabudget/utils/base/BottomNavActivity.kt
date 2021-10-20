package com.danielomeara.buildabudget.utils.base

import android.os.Bundle
import androidx.navigation.ui.setupWithNavController
import com.danielomeara.buildabudget.R
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BottomNavActivity: BaseCompatActivity() {

    abstract fun getBottomNavMenu(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.inflateMenu(getBottomNavMenu())
        bottomNav.setupWithNavController(getNavController())
    }

}