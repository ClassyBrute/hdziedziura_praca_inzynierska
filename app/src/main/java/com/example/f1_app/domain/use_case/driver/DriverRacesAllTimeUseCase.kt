package com.example.f1_app.domain.use_case.driver

import com.example.f1_app.common.Resource

interface DriverRacesAllTimeUseCase {
    suspend fun execute(driverId: String): Resource<String>
}