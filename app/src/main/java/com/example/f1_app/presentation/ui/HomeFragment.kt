package com.example.f1_app.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.f1_app.R
import com.example.f1_app.databinding.FragmentHomeBinding
import com.example.f1_app.presentation.ext.viewModels
import com.example.f1_app.presentation.viewmodels.home.HomeViewModel
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var binding: FragmentHomeBinding? = null
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
        if (binding == null)
            binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycle.addObserver(viewModel)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiEvents.collect {
                        when (it) {
                            is HomeViewModel.Event.FetchingErrorEvent -> Toast.makeText(
                                context, "Error fetching data", Toast.LENGTH_SHORT
                            ).show()

                            is HomeViewModel.Event.NavigateToStart ->
                                findNavController().navigate(R.id.action_home_start)

                            is HomeViewModel.Event.DriverClickEvent -> {
                                val bundle = bundleOf("driverId" to it.item.driverId)
                                findNavController().navigate(
                                    R.id.action_homeFragment_to_driverDetailsFragment,
                                    bundle
                                )
                            }

                            is HomeViewModel.Event.CarouselClickEvent -> Toast.makeText(
                                context, "Race click ${it.item.title}", Toast.LENGTH_SHORT
                            ).show()

                            is HomeViewModel.Event.RaceClickEvent -> Toast.makeText(
                                context, "Race click ${it.item.country}", Toast.LENGTH_SHORT
                            ).show()

                            is HomeViewModel.Event.ConstructorClickEvent -> Toast.makeText(
                                context, "Constructor click ${it.item.name}", Toast.LENGTH_SHORT
                            ).show()

                            is HomeViewModel.Event.ShowDriversEvent -> {
                                viewModel.data.set(viewModel.newList)
                                view?.findViewById<MaterialTextView>(R.id.button_show_all)?.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }

        binding!!.vm = viewModel

        return binding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        lifecycle.removeObserver(viewModel)
    }
}