package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.presentation.ui.StartFragment
import com.example.f1_app.presentation.viewmodels.start.StartViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface StartFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            StartViewModelModule::class,

        ]
    )
    @FragmentScope
    fun contributeStartFragment(): StartFragment
}

@Module
internal interface StartViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(StartViewModel::class)
    fun bindStartVM(viewModel: StartViewModel): ViewModel
}