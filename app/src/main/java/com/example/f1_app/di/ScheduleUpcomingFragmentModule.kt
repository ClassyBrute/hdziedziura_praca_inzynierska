package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.presentation.ui.ScheduleUpcomingFragment
import com.example.f1_app.presentation.viewmodels.schedule.ScheduleUpcomingViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface ScheduleUpcomingFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            ScheduleUpcomingViewModelModule::class,
            NextRaceUseCaseModule::class,
            HomeRepoModule::class,
            ResultsLatestUseCaseModule::class
        ]
    )
    @FragmentScope
    fun contributeScheduleUpcomingFragment(): ScheduleUpcomingFragment
}

@Module
internal interface ScheduleUpcomingViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(ScheduleUpcomingViewModel::class)
    fun bindScheduleUpcomingVM(viewModel: ScheduleUpcomingViewModel): ViewModel
}