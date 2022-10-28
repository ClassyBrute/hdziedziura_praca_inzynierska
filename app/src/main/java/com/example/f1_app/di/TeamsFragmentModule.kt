package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.presentation.ui.TeamsFragment
import com.example.f1_app.presentation.viewmodels.teams.TeamsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface TeamsFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            TeamsViewModelModule::class,
            DriverStandingsUseCaseModule::class,
            ConstructorStandingsUseCaseModule::class,
            HomeRepoModule::class
        ]
    )
    @FragmentScope
    fun contributeTeamsFragment(): TeamsFragment
}

@Module
internal interface TeamsViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(TeamsViewModel::class)
    fun bindTeamsVM(viewModel: TeamsViewModel): ViewModel
}