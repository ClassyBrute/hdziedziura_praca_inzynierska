package com.example.f1_app.presentation.ui

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.f1_app.R
import com.example.f1_app.databinding.FragmentTeamDetailsResultsBinding
import com.example.f1_app.presentation.ext.parentFragmentViewModels
import com.example.f1_app.presentation.ext.viewModels
import com.example.f1_app.presentation.viewmodels.teamDetails.TeamDetailsResultsViewModel
import com.example.f1_app.presentation.viewmodels.teamDetails.TeamDetailsViewModel
import kotlinx.coroutines.launch

class TeamDetailsResultsFragment : BaseFragment() {
    private val viewModel: TeamDetailsResultsViewModel by viewModels()
    private val parentViewModel: TeamDetailsViewModel by parentFragmentViewModels()
    private lateinit var binding: FragmentTeamDetailsResultsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_team_details_results, container, false
        )

        lifecycle.addObserver(viewModel)

        viewModel.teamId = parentViewModel.teamId
        viewModel.teamName = parentViewModel.teamDetails.get()?.name.toString()

        binding.seasonChoice.setOnClickListener {
            val popup = PopupMenu(context, it, Gravity.BOTTOM)
            popup.menuInflater.inflate(R.menu.season_menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem: MenuItem ->
                viewModel.season.set(menuItem.title.toString())
                binding.seasonChoice.text = menuItem.title

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.fetchDriverStandings()
                    viewModel.fetchDriversResults(viewModel.driverIds)
                }.invokeOnCompletion {
                    viewModel.createRecyclerItems()
                }

                true
            }
            popup.show()
        }

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