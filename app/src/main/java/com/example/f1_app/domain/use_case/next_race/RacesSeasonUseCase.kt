package com.example.f1_app.domain.use_case.next_race

import com.example.f1_app.common.Resource
import com.example.f1_app.domain.model.NextRace

interface RacesSeasonUseCase {
    suspend fun execute(season: String): Resource<List<NextRace>>
}