package com.example.f1_app.di

import androidx.lifecycle.ViewModelProvider
import com.example.f1_app.di.scope.ActivityScope
import com.example.f1_app.presentation.ui.MainActivity
import com.example.f1_app.presentation.vmFactory.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ApiModule::class])
internal interface AppModule {
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    @ActivityScope
    fun mainActivityInjector(): MainActivity

    @Binds
    fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

//    companion object {
//        @JvmStatic
//        @Provides
//        fun provideDataStore(application: Application): DataStoreManager {
//            return DataStoreManagerImpl(application.applicationContext)
//        }
//    }
}