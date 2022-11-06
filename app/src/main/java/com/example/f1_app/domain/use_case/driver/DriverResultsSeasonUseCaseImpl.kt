package com.example.f1_app.domain.use_case.driver

import com.example.f1_app.common.Resource
import com.example.f1_app.data.toResultsSeason
import com.example.f1_app.domain.model.DriverResultsSeason
import com.example.f1_app.domain.repository.HomeRepository
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import javax.inject.Inject

class DriverResultsSeasonUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository,
) : DriverResultsSeasonUseCase {

    override suspend fun execute(
        season: String,
        driverId: String
    ): Resource<List<DriverResultsSeason>> {
        return try {
            coroutineScope {
                val result = homeRepository.driverResultsSeason(season, driverId).body()
                Resource.Success(
                    result?.toResultsSeason() ?: emptyList()
                )
            }
        } catch (e: IOException) {
            Resource.Error("connection error", listOf(DriverResultsSeason()))
        }
    }
}