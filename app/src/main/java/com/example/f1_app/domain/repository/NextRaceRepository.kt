package com.example.f1_app.domain.repository

import com.example.f1_app.data.network.dto.NextRacesDto
import retrofit2.Response

interface NextRaceRepository {
    suspend fun nextRace(): Response<NextRacesDto>
}