package com.example.f1_app.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.f1_app.R
import com.example.f1_app.databinding.FragmentTeamsBinding
import com.example.f1_app.presentation.viewmodels.teams.TeamsViewModel

class TeamsFragment : Fragment() {

    private val viewModel: TeamsViewModel by viewModels()
    private lateinit var binding: FragmentTeamsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_teams, container, false
        )

        viewModel.text.observe(viewLifecycleOwner) {
            binding.textTeams.text = it
        }

        binding.vm = viewModel

        return binding.root
    }

}