package com.example.f1_app.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.f1_app.R
import com.example.f1_app.databinding.FragmentDriverDetailsResultsBinding
import com.example.f1_app.databinding.FragmentSchedulePastBinding
import com.example.f1_app.presentation.ext.parentFragmentViewModels
import com.example.f1_app.presentation.ext.viewModels
import com.example.f1_app.presentation.viewmodels.driverDetails.DriverDetailsResultsViewModel
import com.example.f1_app.presentation.viewmodels.driverDetails.DriverDetailsViewModel
import com.example.f1_app.presentation.viewmodels.schedule.SchedulePastViewModel
import com.example.f1_app.presentation.viewmodels.schedule.ScheduleViewModel
import kotlinx.coroutines.launch

class DriverDetailsResultsFragment : BaseFragment() {
    private val viewModel: DriverDetailsResultsViewModel by viewModels()
    private val parentViewModel: DriverDetailsViewModel by parentFragmentViewModels()
    private lateinit var binding: FragmentDriverDetailsResultsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_driver_details_results, container, false
        )

        lifecycle.addObserver(viewModel)

        viewModel.driverId = parentViewModel.driverId

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiEvents.collect {
                        parentViewModel.emitInnerEvents(it)
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