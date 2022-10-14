package com.example.f1_app.domain.use_case.standings

import com.example.f1_app.common.Resource
import com.example.f1_app.domain.model.DriverStandings

interface DriverStandingsUseCase {
    suspend fun execute(): Resource<List<DriverStandings>>
}