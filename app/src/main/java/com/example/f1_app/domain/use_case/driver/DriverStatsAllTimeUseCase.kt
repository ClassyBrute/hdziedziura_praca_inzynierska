package com.example.f1_app.domain.use_case.driver

import com.example.f1_app.common.Resource

interface DriverStatsAllTimeUseCase {
    suspend fun execute(driverId: String): Resource<Map<String, String>>
}