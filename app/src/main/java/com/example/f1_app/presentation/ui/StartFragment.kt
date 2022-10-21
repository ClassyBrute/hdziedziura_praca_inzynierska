package com.example.f1_app.presentation.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.f1_app.R
import com.example.f1_app.databinding.FragmentStartBinding
import com.example.f1_app.presentation.ext.viewModels
import com.example.f1_app.presentation.viewmodels.start.StartViewModel

class StartFragment: BaseFragment() {

    private val viewModel: StartViewModel by viewModels()
    private lateinit var binding: FragmentStartBinding
    override val showToolbar: Boolean
        get() = false
    override val showBottomNavBar: Boolean
        get() = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_start, container, false
        )

        binding.buttonStart.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.video.setVideoURI(Uri.parse("android.resource://com.example.f1_app/" + R.raw.f1_video))
        binding.video.start()
        binding.video.setOnPreparedListener {
            it.isLooping = true
        }

        binding.vm = viewModel

        return binding.root
    }
}