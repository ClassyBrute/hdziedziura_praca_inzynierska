package com.example.f1_app.domain.use_case.driver

import com.example.f1_app.common.Resource
import com.example.f1_app.data.toStatsAllTime
import com.example.f1_app.domain.repository.HomeRepository
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import javax.inject.Inject

class DriverStatsAllTimeUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
) : DriverStatsAllTimeUseCase {

    override suspend fun execute(driverId: String): Resource<Map<String, String>> {
        return try {
            coroutineScope {
                val result = homeRepository.statsAllTime(driverId).body()
                Resource.Success(
                    result?.toStatsAllTime() ?: mapOf()
                )
            }
        } catch (e: IOException) {
            Resource.Error("Connection error")
        }
    }
}