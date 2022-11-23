package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.presentation.ui.RaceDetailsFragment
import com.example.f1_app.presentation.viewmodels.raceDetails.RaceDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface RaceDetailsFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            RaceDetailsViewModelModule::class
        ]
    )
    @FragmentScope
    fun contributeRaceDetailsFragment(): RaceDetailsFragment
}

@Module
internal interface RaceDetailsViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(RaceDetailsViewModel::class)
    fun bindRaceDetailsVM(viewModel: RaceDetailsViewModel): ViewModel
}