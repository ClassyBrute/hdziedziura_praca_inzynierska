package com.example.f1_app.data.network

import com.example.f1_app.data.network.dto.DriverDetailsDto
import com.example.f1_app.data.network.dto.LatestResultsDto
import com.example.f1_app.data.network.dto.NextRacesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApiService {

    @GET("2022/drivers/{driverId}.json")
    suspend fun driverDetails(@Path("driverId") driverId: String): Response<DriverDetailsDto>

    @GET("current/last/results.json")
    suspend fun latestResults(): Response<LatestResultsDto>

    @GET("current.json")
    suspend fun nextRaces(): Response<NextRacesDto>
}