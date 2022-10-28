package com.example.f1_app.domain.repository

import com.example.f1_app.data.network.dto.*
import retrofit2.Response

interface HomeRepository {
    suspend fun driverDetails(driverId: String): Response<DriverDetailsDto>
    suspend fun nextRace(): Response<NextRacesDto>
    suspend fun latestResults(): Response<LatestResultsDto>
    suspend fun driverStandings(): Response<DriverStandingsDto>
    suspend fun constructorStandings(): Response<ConstructorStandingsDto>
    suspend fun driverStandingsSeason(season: String): Response<DriverStandingsDto>
    suspend fun constructorStandingsSeason(season: String): Response<ConstructorStandingsDto>
    suspend fun racesSeason(season: String): Response<NextRacesDto>
}