package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.ui.HomeFragment
import com.example.f1_app.viewmodels.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface HomeFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            HomeViewModelModule::class,
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