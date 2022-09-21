package com.example.f1_app.di

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import com.example.f1_app.presentation.ui.MainActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [HomeFragmentModule::class,
        DashboardFragmentModule::class,

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