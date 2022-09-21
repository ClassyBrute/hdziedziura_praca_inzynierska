package com.example.f1_app.domain.repository

import com.example.f1_app.data.network.dto.DriverDetailsDto
import retrofit2.Response

interface DriverRepository {
    suspend fun driverDetails(driverId: String): Response<DriverDetailsDto>
}