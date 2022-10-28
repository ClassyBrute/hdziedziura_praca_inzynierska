package com.example.f1_app.domain.use_case.standings

import com.example.f1_app.common.Resource
import com.example.f1_app.domain.model.DriverStandings

interface DriverStandingsSeasonUseCase {
    suspend fun execute(season: String): Resource<List<DriverStandings>>
}