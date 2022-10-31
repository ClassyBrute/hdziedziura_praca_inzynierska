package com.example.f1_app.domain.use_case.driver

import com.example.f1_app.common.Resource
import com.example.f1_app.data.toRacesAllTime
import com.example.f1_app.domain.model.RacesAllTime
import com.example.f1_app.domain.repository.HomeRepository
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import javax.inject.Inject

class DriverRacesAllTimeUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
) : DriverRacesAllTimeUseCase {

    override suspend fun execute(driverId: String): Resource<String> {
        return try {
            coroutineScope {
                val result = homeRepository.racesAllTime(driverId).body()
                Resource.Success(
                    result?.toRacesAllTime() ?: ""
                )
            }
        } catch (e: IOException) {
            Resource.Error("Connection error")
        }
    }
}