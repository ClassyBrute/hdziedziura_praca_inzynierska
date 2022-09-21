package com.example.f1_app.data.repositoryImpl

import com.example.f1_app.data.network.RetrofitApiService
import com.example.f1_app.data.network.dto.DriverDetailsDto
import com.example.f1_app.domain.repository.DriverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class DriverRepositoryImpl @Inject constructor(
    private val api: RetrofitApiService
) : DriverRepository {

    override suspend fun driverDetails(driverId: String): Response<DriverDetailsDto> =
        withContext(Dispatchers.IO) {
            return@withContext api.driverDetails(driverId)
        }
}