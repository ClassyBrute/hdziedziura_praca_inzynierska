package com.example.f1_app.di

import com.example.f1_app.data.repositoryImpl.HomeRepositoryImpl
import com.example.f1_app.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module

@Module
internal interface HomeRepoModule {
    @Binds
    fun bindHomeRepository(homeRepository: HomeRepositoryImpl): HomeRepository
}