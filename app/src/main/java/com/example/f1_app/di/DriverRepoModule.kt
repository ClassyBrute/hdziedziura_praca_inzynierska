package com.example.f1_app.di

import com.example.f1_app.data.repositoryImpl.DriverRepositoryImpl
import com.example.f1_app.domain.repository.DriverRepository
import dagger.Binds
import dagger.Module

@Module
internal interface DriverRepoModule {
    @Binds
    fun bindDriverRepository(driverRepositoryImpl: DriverRepositoryImpl): DriverRepository
}