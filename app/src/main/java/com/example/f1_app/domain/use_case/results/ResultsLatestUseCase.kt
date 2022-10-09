package com.example.f1_app.domain.use_case.results

import com.example.f1_app.common.Resource
import com.example.f1_app.domain.model.Results

interface ResultsLatestUseCase {
    suspend fun execute(): Resource<List<Results>>
}