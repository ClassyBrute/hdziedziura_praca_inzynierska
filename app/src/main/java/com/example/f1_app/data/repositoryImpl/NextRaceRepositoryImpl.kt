package com.example.f1_app.data.repositoryImpl

import com.example.f1_app.data.network.RetrofitApiService
import com.example.f1_app.data.network.dto.NextRacesDto
import com.example.f1_app.domain.repository.NextRaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NextRaceRepositoryImpl @Inject constructor(
    private val api: RetrofitApiService
) : NextRaceRepository {

    override suspend fun nextRace(): Response<NextRacesDto> =
        withContext(Dispatchers.IO) {
            return@withContext api.nextRaces()
        }
}