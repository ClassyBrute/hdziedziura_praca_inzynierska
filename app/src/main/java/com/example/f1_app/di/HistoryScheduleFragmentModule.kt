package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.domain.use_case.next_race.RacesSeasonUseCase
import com.example.f1_app.domain.use_case.next_race.RacesSeasonUseCaseImpl
import com.example.f1_app.presentation.ui.HistoryScheduleFragment
import com.example.f1_app.presentation.viewmodels.history.HistoryScheduleViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface HistoryScheduleFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            HistoryScheduleViewModelModule::class,
            HomeRepoModule::class,
            RacesSeasonUseCaseModule::class
        ]
    )
    @FragmentScope
    fun contributeHistoryScheduleFragment(): HistoryScheduleFragment
}

@Module
internal interface HistoryScheduleViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(HistoryScheduleViewModel::class)
    fun bindHistoryScheduleVM(viewModel: HistoryScheduleViewModel): ViewModel
}

@Module
internal interface RacesSeasonUseCaseModule {
    @Binds
    fun bindRacesSeasonUseCase(racesSeasonUseCase: RacesSeasonUseCaseImpl): RacesSeasonUseCase
}