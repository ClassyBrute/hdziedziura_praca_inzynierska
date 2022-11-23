package com.example.f1_app.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.f1_app.R
import com.example.f1_app.databinding.FragmentRaceDetailsBinding
import com.example.f1_app.presentation.ext.viewModels
import com.example.f1_app.presentation.viewmodels.raceDetails.RaceDetailsViewModel

class RaceDetailsFragment : BaseFragment() {
    private val viewModel: RaceDetailsViewModel by viewModels()
    private lateinit var binding: FragmentRaceDetailsBinding
    override val showToolbar: Boolean
        get() = true
    override val showBottomNavBar: Boolean
        get() = true
    override val toolbarTitle: String
        get() = "Race Details"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_race_details, container, false
        )

        lifecycle.addObserver(viewModel)

        viewModel.raceName = arguments?.getString("raceName").toString()
        viewModel.circuitName = arguments?.getString("circuitName").toString()
        viewModel.image = arguments?.getInt("image")!!
        viewModel.map = arguments?.getInt("map")!!

        binding.vm = viewModel

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }
}