package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.presentation.ui.DriverDetailsFragment
import com.example.f1_app.presentation.viewmodels.driverDetails.DriverDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface DriverDetailsFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            DriverDetailsViewModelModule::class,
            HomeRepoModule::class,
            DriverDetailsUseCaseModule::class
        ]
    )
    @FragmentScope
    fun contributeDriverDetailsFragment(): DriverDetailsFragment
}

@Module
internal interface DriverDetailsViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(DriverDetailsViewModel::class)
    fun bindDriverDetailsVM(viewModel: DriverDetailsViewModel): ViewModel
}