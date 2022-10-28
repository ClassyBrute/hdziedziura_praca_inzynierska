package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.domain.use_case.standings.ConstructorStandingsSeasonUseCase
import com.example.f1_app.domain.use_case.standings.ConstructorStandingsSeasonUseCaseImpl
import com.example.f1_app.presentation.ui.HistoryTeamsFragment
import com.example.f1_app.presentation.viewmodels.history.HistoryTeamsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface HistoryTeamsFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            HistoryTeamsViewModelModule::class,
            HomeRepoModule::class,
            ConstructorStandingsSeasonUseCaseModule::class
        ]
    )
    @FragmentScope
    fun contributeHistoryTeamFragment(): HistoryTeamsFragment
}

@Module
internal interface HistoryTeamsViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(HistoryTeamsViewModel::class)
    fun bindHistoryTeamsVM(viewModel: HistoryTeamsViewModel): ViewModel
}

@Module
internal interface ConstructorStandingsSeasonUseCaseModule {
    @Binds
    fun bindConstructorStandingsSeasonUseCase(constructorStandingsSeasonUseCase: ConstructorStandingsSeasonUseCaseImpl): ConstructorStandingsSeasonUseCase
}