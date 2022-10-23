package com.example.f1_app.presentation.ext

import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.f1_app.presentation.ui.BaseFragment
import com.example.f1_app.presentation.ui.MainActivity

@MainThread
inline fun <reified VM: ViewModel> BaseFragment.viewModels() =
    viewModels<VM>(
        ownerProducer = { this },
        factoryProducer = { this.viewModelFactory }
    )

@MainThread
inline fun <reified VM: ViewModel> BaseFragment.parentFragmentViewModels() =
    viewModels<VM>(
        ownerProducer = { this.requireParentFragment() },
        factoryProducer = { this.viewModelFactory }
    )

@MainThread
inline fun <reified VM: ViewModel> BaseFragment.activityViewModels() =
    activityViewModels<VM>(factoryProducer = { this.viewModelFactory })

@MainThread
inline fun <reified VM: ViewModel> MainActivity.viewModels() =
    viewModels<VM>(factoryProducer = {this.viewModelFactory})
