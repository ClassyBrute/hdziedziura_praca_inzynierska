package com.example.f1_app.domain.use_case.constructor

import com.example.f1_app.common.Resource

interface ConstructorStatsUseCase {
    suspend fun execute(constructorId: String): Resource<Map<String, String>>
}