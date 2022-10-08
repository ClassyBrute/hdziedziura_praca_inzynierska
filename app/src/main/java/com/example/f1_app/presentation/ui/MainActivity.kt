package com.example.f1_app.presentation.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.f1_app.R
import com.example.f1_app.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, AppBarConfiguration(navController.graph))

        binding.bottomNavigation.setupWithNavController(navController)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    fun setupToolbar(toolbarTitle: String, showToolbar: Boolean) {
        binding.toolbar.apply {
            visibility = if (showToolbar) {
                View.VISIBLE
            } else {
                View.GONE
            }
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
            title = toolbarTitle
        }
    }

    fun setupBottomNav(showBottomNav: Boolean) {
        binding.bottomNavigation.visibility = if (showBottomNav) {
            View.VISIBLE
        } else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}