package com.example.f1_app.data.repositoryImpl

import com.example.f1_app.data.network.RetrofitApiService
import com.example.f1_app.data.network.dto.LatestResultsDto
import com.example.f1_app.domain.repository.ResultsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class ResultsRepositoryImpl @Inject constructor(
    private val api: RetrofitApiService
) : ResultsRepository {

    override suspend fun latestResults(): Response<LatestResultsDto> =
        withContext(Dispatchers.IO) {
            return@withContext api.latestResults()
        }
}