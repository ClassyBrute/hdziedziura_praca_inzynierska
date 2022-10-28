package com.example.f1_app.data.repositoryImpl

import com.example.f1_app.data.network.RetrofitApiService
import com.example.f1_app.data.network.dto.*
import com.example.f1_app.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: RetrofitApiService
) : HomeRepository {

    override suspend fun driverDetails(driverId: String): Response<DriverDetailsDto> =
        withContext(Dispatchers.IO) {
            return@withContext api.driverDetails(driverId)
        }

    override suspend fun nextRace(): Response<NextRacesDto> =
        withContext(Dispatchers.IO) {
            return@withContext api.nextRaces()
        }

    override suspend fun latestResults(): Response<LatestResultsDto> =
        withContext(Dispatchers.IO) {
            return@withContext api.latestResults()
        }

    override suspend fun driverStandings(): Response<DriverStandingsDto> =
        withContext(Dispatchers.IO) {
            return@withContext api.driverStandings()
        }

    override suspend fun constructorStandings(): Response<ConstructorStandingsDto> =
        withContext(Dispatchers.IO) {
            return@withContext api.constructorStandings()
        }

    override suspend fun driverStandingsSeason(season: String): Response<DriverStandingsDto> =
        withContext(Dispatchers.IO) {
            return@withContext api.driverStandingsSeason(season)
        }

    override suspend fun constructorStandingsSeason(season: String): Response<ConstructorStandingsDto> =
        withContext(Dispatchers.IO) {
            return@withContext api.constructorStandingsSeason(season)
        }

    override suspend fun racesSeason(season: String): Response<NextRacesDto> =
        withContext(Dispatchers.IO) {
            return@withContext api.racesSeason(season)
        }
}