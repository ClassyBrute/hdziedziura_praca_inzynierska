package com.example.f1_app.domain.use_case.driver

import com.example.f1_app.common.Resource
import com.example.f1_app.data.toDriver
import com.example.f1_app.domain.model.Driver
import com.example.f1_app.domain.repository.HomeRepository
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import javax.inject.Inject

class DriverDetailsUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
) : DriverDetailsUseCase {

    override suspend fun execute(driverId: String): Resource<Driver> {
        return try {
            coroutineScope {
                val result = homeRepository.driverDetails(driverId).body()
                Resource.Success(
                    result?.toDriver() ?: Driver()
                )
            }
        } catch (e: IOException) {
            Resource.Error("Connection error")
        }
    }
}