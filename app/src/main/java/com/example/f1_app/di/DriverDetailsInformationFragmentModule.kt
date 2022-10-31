package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.domain.use_case.driver.DriverRacesAllTimeUseCase
import com.example.f1_app.domain.use_case.driver.DriverRacesAllTimeUseCaseImpl
import com.example.f1_app.domain.use_case.driver.DriverStatsAllTimeUseCase
import com.example.f1_app.domain.use_case.driver.DriverStatsAllTimeUseCaseImpl
import com.example.f1_app.presentation.ui.DriverDetailsInformationFragment
import com.example.f1_app.presentation.viewmodels.driverDetails.DriverDetailsInformationViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface DriverDetailsInformationFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            DriverDetailsInformationViewModelModule::class,
            HomeRepoModule::class,
            DriverRacesAllTimeUseCaseModule::class,
            DriverStatsAllTimeUseCaseModule::class,
            DriverDetailsUseCaseModule::class
        ]
    )
    @FragmentScope
    fun contributeDriverDetailsInformationFragment(): DriverDetailsInformationFragment
}

@Module
internal interface DriverDetailsInformationViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(DriverDetailsInformationViewModel::class)
    fun bindDriverDetailsInformationVM(viewModel: DriverDetailsInformationViewModel): ViewModel
}

@Module
internal interface DriverRacesAllTimeUseCaseModule {
    @Binds
    fun bindDriverRacesAllTimeUseCase(racesAllTimeUseCase: DriverRacesAllTimeUseCaseImpl): DriverRacesAllTimeUseCase
}

@Module
internal interface DriverStatsAllTimeUseCaseModule {
    @Binds
    fun bindDriverStatsAllTimeUseCase(statsAllTimeUseCase: DriverStatsAllTimeUseCaseImpl): DriverStatsAllTimeUseCase
}