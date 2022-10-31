package com.example.f1_app.domain.use_case.driver

import com.example.f1_app.common.Resource
import com.example.f1_app.domain.model.RacesAllTime

interface DriverRacesAllTimeUseCase {
    suspend fun execute(driverId: String): Resource<String>
}