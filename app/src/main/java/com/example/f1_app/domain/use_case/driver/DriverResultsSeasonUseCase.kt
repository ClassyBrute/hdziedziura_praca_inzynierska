package com.example.f1_app.domain.use_case.driver

import com.example.f1_app.common.Resource
import com.example.f1_app.domain.model.DriverResultsSeason

interface DriverResultsSeasonUseCase {
    suspend fun execute(season: String, driverId: String): Resource<List<DriverResultsSeason>>
}