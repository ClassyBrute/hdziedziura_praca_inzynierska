package com.example.f1_app.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.f1_app.R
import com.example.f1_app.databinding.FragmentDriverDetailsBinding
import com.example.f1_app.presentation.ext.viewModels
import com.example.f1_app.presentation.ui.adapter.ViewPagerAdapterDriver
import com.example.f1_app.presentation.viewmodels.driverDetails.DriverDetailsViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class DriverDetailsFragment : BaseFragment() {

    private val viewModel: DriverDetailsViewModel by viewModels()
    private lateinit var binding: FragmentDriverDetailsBinding
    override val showToolbar: Boolean
        get() = true
    override val showBottomNavBar: Boolean
        get() = true
    override val toolbarTitle: String
        get() = "Driver Details"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_driver_details, container, false
        )

        lifecycle.addObserver(viewModel)

        viewModel.driverId = arguments?.getString("driverId").toString()

        val adapter = ViewPagerAdapterDriver(this, 2)
        binding.viewpager2.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.viewpager2) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.title_information)
                1 -> tab.text = getString(R.string.title_results)
            }
        }.attach()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiEvents.collect {
                        when (it) {
                            is DriverDetailsViewModel.Event.FetchingErrorEvent -> Toast.makeText(
                                context, getString(R.string.error_fetching), Toast.LENGTH_SHORT
                            ).show()
                            is DriverDetailsViewModel.Event.RaceClickEvent -> {
                                val bundle = bundleOf(
                                    "raceName" to it.item.raceName,
                                    "circuitName" to it.item.circuitName,
                                    "image" to it.item.image,
                                    "map" to it.item.map
                                )
                                findNavController().navigate(
                                    R.id.action_driverDetailsFragment_to_raceDetailsFragment,
                                    bundle
                                )
                            }
                            is DriverDetailsViewModel.Event.EmptyResults -> Toast.makeText(
                                context, "Driver had not raced this season", Toast.LENGTH_SHORT
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