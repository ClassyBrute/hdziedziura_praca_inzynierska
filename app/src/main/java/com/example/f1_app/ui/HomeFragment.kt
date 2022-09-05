package com.example.f1_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.f1_app.R
import com.example.f1_app.databinding.FragmentHomeBinding
import com.example.f1_app.viewmodels.HomeViewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        viewModel.text.observe(viewLifecycleOwner) {
            binding.textHome.text = it
        }

        binding.vm = viewModel

        return binding.root
    }
}