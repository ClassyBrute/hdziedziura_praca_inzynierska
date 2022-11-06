package com.example.f1_app.data.network

import com.example.f1_app.data.network.dto.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApiService {

    @GET("drivers/{driverId}.json")
    suspend fun driverDetails(@Path("driverId") driverId: String): Response<DriverDetailsDto>

    @GET("current/last/results.json")
    suspend fun latestResults(): Response<LatestResultsDto>

    @GET("current.json")
    suspend fun nextRaces(): Response<NextRacesDto>

    @GET("current/driverStandings.json")
    suspend fun driverStandings(): Response<DriverStandingsDto>

    @GET("current/constructorStandings.json")
    suspend fun constructorStandings(): Response<ConstructorStandingsDto>

    @GET("{season}/driverStandings.json")
    suspend fun driverStandingsSeason(@Path("season") season: String): Response<DriverStandingsDto>

    @GET("{season}/constructorStandings.json")
    suspend fun constructorStandingsSeason(@Path("season") season: String): Response<ConstructorStandingsDto>

    @GET("{season}.json")
    suspend fun racesSeason(@Path("season") season: String): Response<NextRacesDto>

    @GET("drivers/{driverId}/driverStandings.json")
    suspend fun statsAllTime(@Path("driverId") driverId: String): Response<DriverStandingsDto>

    @GET("drivers/{driverId}/status.json")
    suspend fun racesAllTime(@Path("driverId") driverId: String): Response<RacesAllTimeDto>

    @GET("{season}/drivers/{driverId}/results.json")
    suspend fun driverResultsSeason(@Path("season") season: String, @Path("driverId") driverId: String): Response<LatestResultsDto>
}