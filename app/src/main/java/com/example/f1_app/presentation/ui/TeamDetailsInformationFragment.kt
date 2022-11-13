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
import com.example.f1_app.databinding.FragmentTeamDetailsInformationBinding
import com.example.f1_app.presentation.ext.parentFragmentViewModels
import com.example.f1_app.presentation.ext.viewModels
import com.example.f1_app.presentation.viewmodels.teamDetails.TeamDetailsInformationViewModel
import com.example.f1_app.presentation.viewmodels.teamDetails.TeamDetailsViewModel
import kotlinx.coroutines.launch

class TeamDetailsInformationFragment : BaseFragment() {
    private val viewModel: TeamDetailsInformationViewModel by viewModels()
    private val parentViewModel: TeamDetailsViewModel by parentFragmentViewModels()
    private lateinit var binding: FragmentTeamDetailsInformationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_team_details_information, container, false
        )

        lifecycle.addObserver(viewModel)

        viewModel.teamId = parentViewModel.teamId
        viewModel.season = parentViewModel.season

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