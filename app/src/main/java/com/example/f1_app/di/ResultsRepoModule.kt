package com.example.f1_app.di

import com.example.f1_app.data.repositoryImpl.ResultsRepositoryImpl
import com.example.f1_app.domain.repository.ResultsRepository
import dagger.Binds
import dagger.Module

@Module
internal interface ResultsRepoModule {
    @Binds
    fun bindResultsRespository(resultsRepository: ResultsRepositoryImpl): ResultsRepository
}
