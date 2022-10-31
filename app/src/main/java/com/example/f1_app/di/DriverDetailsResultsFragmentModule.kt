package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.domain.use_case.driver.DriverRacesAllTimeUseCase
import com.example.f1_app.domain.use_case.driver.DriverRacesAllTimeUseCaseImpl
import com.example.f1_app.domain.use_case.next_race.RacesSeasonUseCase
import com.example.f1_app.domain.use_case.next_race.RacesSeasonUseCaseImpl
import com.example.f1_app.presentation.ui.DriverDetailsResultsFragment
import com.example.f1_app.presentation.viewmodels.driverDetails.DriverDetailsResultsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface DriverDetailsResultsFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            DriverDetailsResultsViewModelModule::class,
            HomeRepoModule::class,
        ]
    )
    @FragmentScope
    fun contributeDriverDetailsResultsFragment(): DriverDetailsResultsFragment
}

@Module
internal interface DriverDetailsResultsViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(DriverDetailsResultsViewModel::class)
    fun bindDriverDetailsResultsVM(viewModel: DriverDetailsResultsViewModel): ViewModel
}