package com.example.f1_app.domain.repository

import com.example.f1_app.data.network.dto.LatestResultsDto
import retrofit2.Response

interface ResultsRepository {
    suspend fun latestResults(): Response<LatestResultsDto>
}