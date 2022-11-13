package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.presentation.ui.TeamDetailsFragment
import com.example.f1_app.presentation.viewmodels.teamDetails.TeamDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface TeamDetailsFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            TeamDetailsViewModelModule::class,
            HomeRepoModule::class,
            ConstructorDetailsUseCaseModule::class,
        ]
    )
    @FragmentScope
    fun contributeTeamDetailsFragment(): TeamDetailsFragment
}

@Module
internal interface TeamDetailsViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(TeamDetailsViewModel::class)
    fun bindTeamDetailsVM(viewModel: TeamDetailsViewModel): ViewModel
}