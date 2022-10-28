package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.presentation.ui.DriversFragment
import com.example.f1_app.presentation.viewmodels.drivers.DriversViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface DriversFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            DriversViewModelModule::class,
            DriverStandingsUseCaseModule::class,
            HomeRepoModule::class
        ]
    )
    @FragmentScope
    fun contributeDriversFragment(): DriversFragment
}

@Module
internal interface DriversViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(DriversViewModel::class)
    fun bindDriversVM(viewModel: DriversViewModel): ViewModel
}