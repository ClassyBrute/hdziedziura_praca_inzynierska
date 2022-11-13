package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.domain.use_case.constructor.ConstructorDetailsUseCase
import com.example.f1_app.domain.use_case.constructor.ConstructorDetailsUseCaseImpl
import com.example.f1_app.domain.use_case.driver.DriverDetailsUseCase
import com.example.f1_app.domain.use_case.driver.DriverDetailsUseCaseImpl
import com.example.f1_app.domain.use_case.next_race.NextRaceUseCase
import com.example.f1_app.domain.use_case.next_race.NextRaceUseCaseImpl
import com.example.f1_app.domain.use_case.results.ResultsLatestUseCase
import com.example.f1_app.domain.use_case.results.ResultsLatestUseCaseImpl
import com.example.f1_app.domain.use_case.standings.ConstructorStandingsUseCase
import com.example.f1_app.domain.use_case.standings.ConstructorStandingsUseCaseImpl
import com.example.f1_app.domain.use_case.standings.DriverStandingsUseCase
import com.example.f1_app.domain.use_case.standings.DriverStandingsUseCaseImpl
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
            HomeRepoModule::class,
            HomeViewModelModule::class,
            DriverDetailsUseCaseModule::class,
            ConstructorDetailsUseCaseModule::class,
            ResultsLatestUseCaseModule::class,
            NextRaceUseCaseModule::class,
            DriverStandingsUseCaseModule::class,
            ConstructorStandingsUseCaseModule::class,
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
internal interface ConstructorDetailsUseCaseModule {
    @Binds
    fun bindConstructorDetailsUseCase(constructorDetailsUseCase: ConstructorDetailsUseCaseImpl): ConstructorDetailsUseCase
}

@Module
internal interface ResultsLatestUseCaseModule {
    @Binds
    fun bindResultsLatestUseCase(resultsLatestUseCase: ResultsLatestUseCaseImpl): ResultsLatestUseCase
}

@Module
internal interface NextRaceUseCaseModule {
    @Binds
    fun bindNextRaceUseCase(nextRaceUseCase: NextRaceUseCaseImpl): NextRaceUseCase
}

@Module
internal interface DriverStandingsUseCaseModule {
    @Binds
    fun bindDriverStandingsUseCase(driverStandingsUseCase: DriverStandingsUseCaseImpl): DriverStandingsUseCase
}

@Module
internal interface ConstructorStandingsUseCaseModule {
    @Binds
    fun bindConstructorStandingsUseCase(constructorStandingsUseCase: ConstructorStandingsUseCaseImpl): ConstructorStandingsUseCase
}