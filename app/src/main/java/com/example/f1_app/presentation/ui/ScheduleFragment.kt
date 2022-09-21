package com.example.f1_app.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.f1_app.R
import com.example.f1_app.databinding.FragmentScheduleBinding
import com.example.f1_app.presentation.viewmodels.schedule.ScheduleViewModel

class ScheduleFragment : Fragment() {

    private val viewModel: ScheduleViewModel by viewModels()
    private lateinit var binding: FragmentScheduleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_schedule, container, false
        )

        viewModel.text.observe(viewLifecycleOwner) {
            binding.textSchedule.text = it
        }

        binding.vm = viewModel

        return binding.root

    }
}