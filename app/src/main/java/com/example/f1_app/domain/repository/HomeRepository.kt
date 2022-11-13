package com.example.f1_app.domain.repository

import com.example.f1_app.data.network.dto.*
import retrofit2.Response

interface HomeRepository {
    suspend fun driverDetails(driverId: String): Response<DriverDetailsDto>
    suspend fun constructorDetails(constructorId: String): Response<ConstructorDetailsDto>
    suspend fun nextRace(): Response<NextRacesDto>
    suspend fun latestResults(): Response<LatestResultsDto>
    suspend fun driverStandings(): Response<DriverStandingsDto>
    suspend fun constructorStandings(): Response<ConstructorStandingsDto>
    suspend fun driverStandingsSeason(season: String): Response<DriverStandingsDto>
    suspend fun constructorStandingsSeason(season: String): Response<ConstructorStandingsDto>
    suspend fun racesSeason(season: String): Response<NextRacesDto>
    suspend fun statsAllTime(driverId: String): Response<DriverStandingsDto>
    suspend fun racesAllTime(driverId: String): Response<RacesAllTimeDto>
    suspend fun driverResultsSeason(season: String, driverId: String): Response<LatestResultsDto>
    suspend fun constructorStats(constructorId: String): Response<ConstructorStandingsDto>
    suspend fun firstRace(constructorId: String): Response<SeasonsDto>
}