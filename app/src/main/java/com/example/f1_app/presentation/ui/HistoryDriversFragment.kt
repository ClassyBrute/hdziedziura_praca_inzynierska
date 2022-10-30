package com.example.f1_app.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.f1_app.R
import com.example.f1_app.databinding.FragmentHistoryDriversBinding
import com.example.f1_app.presentation.ext.parentFragmentViewModels
import com.example.f1_app.presentation.ext.viewModels
import com.example.f1_app.presentation.viewmodels.history.HistoryDriversViewModel
import com.example.f1_app.presentation.viewmodels.history.HistoryViewModel
import kotlinx.coroutines.launch

class HistoryDriversFragment : BaseFragment() {
    private val viewModel: HistoryDriversViewModel by viewModels()
    private val parentViewModel: HistoryViewModel by parentFragmentViewModels()
    private lateinit var binding: FragmentHistoryDriversBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_history_drivers, container, false
        )

        lifecycle.addObserver(viewModel)

        viewModel.season.set(parentViewModel.season.get())

        parentViewModel.season.addOnPropertyChangedCallback(object: OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                viewModel.season.set(parentViewModel.season.get())
            }
        })

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