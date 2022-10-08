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
import com.example.f1_app.databinding.FragmentHomeBinding
import com.example.f1_app.presentation.ext.viewModels
import com.example.f1_app.presentation.viewmodels.home.HomeViewModel
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    override val showToolbar: Boolean
        get() = false
    override val showBottomNavBar: Boolean
        get() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        lifecycle.addObserver(viewModel)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiEvents.collect {
                        when (it) {
                            is HomeViewModel.Event.FetchingErrorEvent -> Toast.makeText(
                                context, "Error fetching data", Toast.LENGTH_SHORT
                            ).show()

                            is HomeViewModel.Event.FetchingDoneEvent -> assignImages()

                            is HomeViewModel.Event.DriverClickEvent -> Toast.makeText(
                                context, "Driver click ${it.position}", Toast.LENGTH_SHORT
                            ).show()

                            is HomeViewModel.Event.CarouselClickEvent -> Toast.makeText(
                                context, "Race click ${it.position}", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        binding.vm = viewModel

        return binding.root
    }

    private fun assignImages() {
        viewModel.latestList.forEach {
            val name = it.name.lowercase()
                .replace("\n", "")
                .replace("é", "e")

            it.image = resources.getIdentifier(
                name,
                "drawable",
                requireActivity().packageName
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }
}