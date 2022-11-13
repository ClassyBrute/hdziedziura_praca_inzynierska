package com.example.f1_app.domain.use_case.constructor

import com.example.f1_app.common.Resource
import com.example.f1_app.data.toConstructorStats
import com.example.f1_app.domain.repository.HomeRepository
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import javax.inject.Inject

class ConstructorStatsUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
) : ConstructorStatsUseCase {

    override suspend fun execute(constructorId: String): Resource<Map<String, String>> {
        return try {
            coroutineScope {
                val result = homeRepository.constructorStats(constructorId).body()
                Resource.Success(
                    result?.toConstructorStats() ?: emptyMap()
                )
            }
        } catch (e: IOException) {
            Resource.Error("Connection error")
        }
    }
}