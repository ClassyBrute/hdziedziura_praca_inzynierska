package com.example.f1_app.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.f1_app.R
import com.example.f1_app.databinding.FragmentScheduleBinding
import com.example.f1_app.presentation.ui.adapter.ViewPagerAdapter
import com.example.f1_app.presentation.viewmodels.schedule.ScheduleViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class ScheduleFragment : BaseFragment() {

    private val viewModel: ScheduleViewModel by viewModels()
    private lateinit var binding: FragmentScheduleBinding
    override val showToolbar: Boolean
        get() = true
    override val showBottomNavBar: Boolean
        get() = true
    override val toolbarTitle: String
        get() = "Schedule"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_schedule, container, false
        )

        lifecycle.addObserver(viewModel)

        val adapter = ViewPagerAdapter(this, 2)
        binding.viewpager2.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.viewpager2) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.upcoming)
                1 -> tab.text = getString(R.string.past)
            }
        }.attach()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiEvents.collect {
                        when (it) {
                            is ScheduleViewModel.Event.RaceClickEvent -> Toast.makeText(
                                context, "Race ${it.item.country} clicked", Toast.LENGTH_SHORT
                            ).show()
                            is ScheduleViewModel.Event.FetchingErrorEvent -> Toast.makeText(
                                context, getString(R.string.error_fetching), Toast.LENGTH_SHORT
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