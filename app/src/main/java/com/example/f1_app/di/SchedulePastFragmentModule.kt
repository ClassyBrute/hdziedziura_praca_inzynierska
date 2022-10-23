package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.presentation.ui.SchedulePastFragment
import com.example.f1_app.presentation.viewmodels.schedule.SchedulePastViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface SchedulePastFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            SchedulePastViewModelModule::class,
            NextRaceUseCaseModule::class,
            HomeRepoModule::class,
            ResultsLatestUseCaseModule::class
        ]
    )
    @FragmentScope
    fun contributeSchedulePastFragment(): SchedulePastFragment
}

@Module
internal interface SchedulePastViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(SchedulePastViewModel::class)
    fun bindSchedulePastVM(viewModel: SchedulePastViewModel): ViewModel
}