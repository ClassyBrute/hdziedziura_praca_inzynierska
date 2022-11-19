package com.example.f1_app.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.f1_app.R
import com.example.f1_app.databinding.ActivityMainBinding
import com.example.f1_app.presentation.ext.viewModels
import com.example.f1_app.presentation.viewmodels.MainActivityViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun androidInjector(): AndroidInjector<Any> {
        return fragmentInjector
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        lifecycle.addObserver(viewModel)
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.scheduleFragment,
                R.id.driversFragment,
                R.id.teamFragment,
                R.id.historyFragment
            )
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        binding.bottomNavigation.setOnItemReselectedListener {
            when (it) {
                binding.bottomNavigation.menu[0] -> {
                    if (navController.currentDestination?.id != R.id.homeFragment)
                        navController.popBackStack()
                }
                binding.bottomNavigation.menu[1] -> {
//                    if (navController.currentDestination?.id == R.id.shopProductListFragment)
//                        navController.popBackStack()
                }
                binding.bottomNavigation.menu[2] -> {
                    if (navController.currentDestination?.id != R.id.driversFragment)
                        navController.popBackStack()
                }
                binding.bottomNavigation.menu[3] -> {
                    if (navController.currentDestination?.id != R.id.teamFragment)
                        navController.popBackStack()
                }
                binding.bottomNavigation.menu[4] -> {
                    if (navController.currentDestination?.id != R.id.historyFragment)
                        navController.popBackStack()
                }
            }
        }

        binding.bottomNavigation.setupWithNavController(navController)
    }

    fun setupToolbar(toolbarTitle: String, showToolbar: Boolean, showSeason: Boolean) {
        binding.toolbar.apply {
            visibility = if (showToolbar) {
                View.VISIBLE
            } else {
                View.GONE
            }
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
            binding.toolbarTitle.text = toolbarTitle
            title = null

            binding.toolbarSeason.visibility = if (showSeason) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    fun setupBottomNav(showBottomNav: Boolean) {
        binding.bottomNavigation.visibility = if (showBottomNav) {
            View.VISIBLE
        } else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}