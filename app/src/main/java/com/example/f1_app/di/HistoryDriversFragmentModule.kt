package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.domain.use_case.standings.DriverStandingsSeasonUseCase
import com.example.f1_app.domain.use_case.standings.DriverStandingsSeasonUseCaseImpl
import com.example.f1_app.presentation.ui.HistoryDriversFragment
import com.example.f1_app.presentation.viewmodels.history.HistoryDriversViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface HistoryDriversFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            HistoryDriversViewModelModule::class,
            HomeRepoModule::class,
            DriverStandingsSeasonUseCaseModule::class,

        ]
    )
    @FragmentScope
    fun contributeHistoryDriversFragment(): HistoryDriversFragment
}

@Module
internal interface HistoryDriversViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(HistoryDriversViewModel::class)
    fun bindHisotryDriversVM(viewModel: HistoryDriversViewModel): ViewModel
}

@Module
internal interface DriverStandingsSeasonUseCaseModule {
    @Binds
    fun bindDriverStandingsSeasonUseCase(driverStandingsSeasonUseCase: DriverStandingsSeasonUseCaseImpl): DriverStandingsSeasonUseCase
}