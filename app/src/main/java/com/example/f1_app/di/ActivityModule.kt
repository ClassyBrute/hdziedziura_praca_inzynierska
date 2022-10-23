package com.example.f1_app.di

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.ActivityScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.presentation.ui.MainActivity
import com.example.f1_app.presentation.viewmodels.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ActivityViewModelModule::class,
        HomeFragmentModule::class,
        ScheduleFragmentModule::class,
        StartFragmentModule::class,
        ScheduleUpcomingFragmentModule::class,
        SchedulePastFragmentModule::class
    ]
)
interface ActivityModule {

    @Binds
    fun bindsMainActivity(activity: MainActivity): AppCompatActivity

    companion object {
        @Provides
        fun resources(activity: AppCompatActivity): Resources = activity.resources
    }
}

@Module
internal interface ActivityViewModelModule {
    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(MainActivityViewModel::class)
    fun bindMainActivityVM(MainActivityViewModel: MainActivityViewModel): ViewModel
}