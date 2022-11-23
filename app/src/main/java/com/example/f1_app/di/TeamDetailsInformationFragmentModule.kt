package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.domain.use_case.constructor.ConstructorFirstRaceUseCase
import com.example.f1_app.domain.use_case.constructor.ConstructorFirstRaceUseCaseImpl
import com.example.f1_app.domain.use_case.constructor.ConstructorStatsUseCase
import com.example.f1_app.domain.use_case.constructor.ConstructorStatsUseCaseImpl
import com.example.f1_app.presentation.ui.TeamDetailsInformationFragment
import com.example.f1_app.presentation.viewmodels.teamDetails.TeamDetailsInformationViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface TeamDetailsInformationFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            TeamDetailsInformationViewModelModule::class,
            HomeRepoModule::class,
            ConstructorDetailsUseCaseModule::class,
            ConstructorStatsUseCaseModule::class,
            ConstructorFirstRaceUseCaseModule::class,
            DriverStandingsSeasonUseCaseModule::class,
        ]
    )
    @FragmentScope
    fun contributeTeamDetailsInformationFragment(): TeamDetailsInformationFragment
}

@Module
internal interface TeamDetailsInformationViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(TeamDetailsInformationViewModel::class)
    fun bindTeamDetailsInformationVM(viewModel: TeamDetailsInformationViewModel): ViewModel
}

@Module
internal interface ConstructorStatsUseCaseModule {
    @Binds
    fun bindConstructorStatsUseCase(constructorStatsUseCase: ConstructorStatsUseCaseImpl): ConstructorStatsUseCase
}

@Module
internal interface ConstructorFirstRaceUseCaseModule {
    @Binds
    fun bindConstructorFirstRaceUseCase(constructorFirstRaceUseCase: ConstructorFirstRaceUseCaseImpl): ConstructorFirstRaceUseCase
}