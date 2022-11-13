package com.example.f1_app.domain.use_case.constructor

import com.example.f1_app.common.Resource
import com.example.f1_app.domain.model.ConstructorRace

interface ConstructorFirstRaceUseCase {
    suspend fun execute(constructorId: String): Resource<ConstructorRace>
}