package com.example.f1_app.presentation.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
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

        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.scheduleFragment))
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

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

            binding.toolbarSeason.setOnClickListener {
                val popup = PopupMenu(context, it)
                popup.menuInflater.inflate(R.menu.season_menu, popup.menu)
                popup.setOnMenuItemClickListener { menuItem: MenuItem ->
                    Toast.makeText(context, menuItem.title, Toast.LENGTH_SHORT).show()
                    true
                }
                popup.show()
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