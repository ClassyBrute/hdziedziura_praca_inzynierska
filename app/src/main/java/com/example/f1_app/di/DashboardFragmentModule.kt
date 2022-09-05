package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.ui.ScheduleFragment
import com.example.f1_app.viewmodels.ScheduleViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface DashboardFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            DashboardViewModelModule::class,

        ]
    )
    @FragmentScope
    fun contributeDashboardFragment(): ScheduleFragment
}

@Module
internal interface DashboardViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(ScheduleViewModel::class)
    fun bindDashboardVM(viewModel: ScheduleViewModel): ViewModel
}