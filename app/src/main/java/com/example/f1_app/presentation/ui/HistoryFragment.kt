package com.example.f1_app.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.f1_app.R
import com.example.f1_app.databinding.FragmentHistoryBinding
import com.example.f1_app.presentation.ext.viewModels
import com.example.f1_app.presentation.ui.adapter.ViewPagerAdapterHistory
import com.example.f1_app.presentation.viewmodels.history.HistoryViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class HistoryFragment : BaseFragment() {

    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var binding: FragmentHistoryBinding
    override val showToolbar: Boolean
        get() = true
    override val showBottomNavBar: Boolean
        get() = true
    override val toolbarTitle: String
        get() = "History"
    override val showSeason: Boolean
        get() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_history, container, false
        )

        lifecycle.addObserver(viewModel)

        val adapter = ViewPagerAdapterHistory(this, 3)
        binding.viewpager2.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.viewpager2) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.title_drivers)
                1 -> tab.text = getString(R.string.title_team)
                2 -> tab.text = getString(R.string.title_schedule)
            }
        }.attach()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiEvents.collect {
                        when (it) {
                            is HistoryViewModel.Event.FetchingErrorEvent -> Toast.makeText(
                                context, getString(R.string.error_fetching), Toast.LENGTH_SHORT
                            ).show()
                            is HistoryViewModel.Event.DriverClickEvent -> Toast.makeText(
                                context, "Driver ${it.item.name} click", Toast.LENGTH_SHORT
                            ).show()
                            is HistoryViewModel.Event.ConstructorClickEvent -> Toast.makeText(
                                context, "Team ${it.item.name} click", Toast.LENGTH_SHORT
                            ).show()
                            is HistoryViewModel.Event.RaceClickEvent -> Toast.makeText(
                                context, "Race ${it.item.country} click", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        binding.vm = viewModel

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }
}