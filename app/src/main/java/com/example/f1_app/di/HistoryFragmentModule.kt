package com.example.f1_app.di

import androidx.lifecycle.ViewModel
import com.example.f1_app.di.scope.FragmentScope
import com.example.f1_app.di.scope.ViewModelKey
import com.example.f1_app.presentation.ui.HistoryFragment
import com.example.f1_app.presentation.viewmodels.history.HistoryViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface HistoryFragmentModule {
    @ContributesAndroidInjector(
        modules = [
            HistoryViewModelModule::class,

        ]
    )
    @FragmentScope
    fun contributeHistoryFragment(): HistoryFragment
}

@Module
internal interface HistoryViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(HistoryViewModel::class)
    fun bindHistoryVM(viewModel: HistoryViewModel): ViewModel
}