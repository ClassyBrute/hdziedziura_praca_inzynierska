package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.presentation.ui.TeamDetailsResultsFragment
import com.example.f1_app.presentation.viewmodels.teamDetails.TeamDetailsResultsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface TeamDetailsResultsFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            TeamDetailsResultsViewModelModule::class,
            HomeRepoModule::class,
        ]
    )
    @FragmentScope
    fun contributeTeamDetailsResultsFragment(): TeamDetailsResultsFragment
}

@Module
internal interface TeamDetailsResultsViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(TeamDetailsResultsViewModel::class)
    fun bindTeamDetailsResultsVM(viewModel: TeamDetailsResultsViewModel): ViewModel
}
