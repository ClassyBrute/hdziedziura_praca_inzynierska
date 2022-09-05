package com.example.f1_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.f1_app.R
import com.example.f1_app.databinding.FragmentDriversBinding
import com.example.f1_app.viewmodels.DriversViewModel

class DriversFragment : Fragment() {

    private val viewModel: DriversViewModel by viewModels()
    private lateinit var binding: FragmentDriversBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_drivers, container, false
        )

        viewModel.text.observe(viewLifecycleOwner) {
            binding.textDrivers.text = it
        }

        binding.vm = viewModel

        return binding.root
    }
}