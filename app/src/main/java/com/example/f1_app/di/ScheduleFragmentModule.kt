package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.presentation.ui.ScheduleFragment
import com.example.f1_app.presentation.viewmodels.schedule.ScheduleViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface ScheduleFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            ScheduleViewModelModule::class,

        ]
    )
    @FragmentScope
    fun contributeScheduleFragment(): ScheduleFragment
}

@Module
internal interface ScheduleViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(ScheduleViewModel::class)
    fun bindScheduleVM(viewModel: ScheduleViewModel): ViewModel
}