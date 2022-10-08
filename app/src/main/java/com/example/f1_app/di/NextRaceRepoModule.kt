package com.example.f1_app.di

import com.example.f1_app.data.repositoryImpl.NextRaceRepositoryImpl
import com.example.f1_app.domain.repository.NextRaceRepository
import dagger.Binds
import dagger.Module

@Module
internal interface NextRaceRepoModule {
    @Binds
    fun bindNextRaceRepository(nextRaceRepository: NextRaceRepositoryImpl): NextRaceRepository
}