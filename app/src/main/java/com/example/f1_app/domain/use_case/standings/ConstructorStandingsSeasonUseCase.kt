package com.example.f1_app.domain.use_case.standings

import com.example.f1_app.common.Resource
import com.example.f1_app.domain.model.ConstructorStandings

interface ConstructorStandingsSeasonUseCase {
    suspend fun execute(season: String): Resource<List<ConstructorStandings>>
}