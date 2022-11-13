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
import com.example.f1_app.databinding.FragmentTeamDetailsBinding
import com.example.f1_app.presentation.ext.viewModels
import com.example.f1_app.presentation.ui.adapter.ViewPagerAdapterTeam
import com.example.f1_app.presentation.viewmodels.teamDetails.TeamDetailsViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class TeamDetailsFragment : BaseFragment() {

    private val viewModel: TeamDetailsViewModel by viewModels()
    private lateinit var binding: FragmentTeamDetailsBinding
    override val showToolbar: Boolean
        get() = true
    override val showBottomNavBar: Boolean
        get() = true
    override val toolbarTitle: String
        get() = "Team Details"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_team_details, container, false
        )

        lifecycle.addObserver(viewModel)

        viewModel.teamId = arguments?.getString("teamId").toString()
        viewModel.season = arguments?.getString("season").toString()

        val adapter = ViewPagerAdapterTeam(this, 2)
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
                            is TeamDetailsViewModel.Event.FetchingErrorEvent -> Toast.makeText(
                                context, getString(R.string.error_fetching), Toast.LENGTH_SHORT
                            ).show()
                            is TeamDetailsViewModel.Event.RaceClickEvent -> Toast.makeText(
                                context, "Race ${it.item.country} click", Toast.LENGTH_SHORT
                            ).show()
                            is TeamDetailsViewModel.Event.EmptyResults -> Toast.makeText(
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