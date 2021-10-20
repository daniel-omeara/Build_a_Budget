package com.danielomeara.buildabudget.features.bottomnavigation

import com.danielomeara.buildabudget.R
import com.danielomeara.buildabudget.utils.base.BottomNavActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BottomNavActivity() {
    override fun getActivityView(): Int = R.layout.activity_bottom_nav

    override fun getNavGraph(): Int = R.navigation.nav_graph_main

    override fun getBottomNavMenu(): Int = R.menu.bottom_navigation
}