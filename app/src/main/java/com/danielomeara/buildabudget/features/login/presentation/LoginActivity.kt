package com.danielomeara.buildabudget.features.login.presentation

import com.danielomeara.buildabudget.R
import com.danielomeara.buildabudget.utils.base.BaseCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: BaseCompatActivity() {
    override fun getActivityView(): Int = R.layout.activity

    override fun getNavGraph(): Int = R.navigation.nav_graph_login
}