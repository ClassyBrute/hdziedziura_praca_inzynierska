package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.domain.use_case.driver.DriverDetailsUseCase
import com.example.f1_app.domain.use_case.driver.DriverDetailsUseCaseImpl
import com.example.f1_app.domain.use_case.results.ResultsLatestUseCase
import com.example.f1_app.domain.use_case.results.ResultsLatestUseCaseImpl
import com.example.f1_app.presentation.ui.HomeFragment
import com.example.f1_app.presentation.viewmodels.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface HomeFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            HomeViewModelModule::class,
            DriverRepoModule::class,
            DriverDetailsUseCaseModule::class,
            ResultsRepoModule::class,
            ResultsLatestUseCaseModule::class,
        ]
    )
    @FragmentScope
    fun contributeHomeFragment(): HomeFragment
}

@Module
internal interface HomeViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeVM(viewModel: HomeViewModel): ViewModel
}

@Module
internal interface DriverDetailsUseCaseModule {
    @Binds
    fun bindDriverDetailsUseCase(driverDetailsUseCase: DriverDetailsUseCaseImpl): DriverDetailsUseCase
}

@Module
internal interface ResultsLatestUseCaseModule {
    @Binds
    fun bindResultsLatestUseCase(resultsLatestUseCase: ResultsLatestUseCaseImpl): ResultsLatestUseCase
}