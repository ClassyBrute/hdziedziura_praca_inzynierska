package com.example.f1_app.domain.use_case.next_race

import com.example.f1_app.common.Resource
import com.example.f1_app.domain.model.NextRace

interface NextRaceUseCase {
    suspend fun execute(): Resource<MutableList<NextRace>>
}