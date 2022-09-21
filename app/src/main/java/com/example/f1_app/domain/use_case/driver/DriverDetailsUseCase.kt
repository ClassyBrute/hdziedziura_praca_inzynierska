package com.example.f1_app.domain.use_case.driver

import com.example.f1_app.common.Resource
import com.example.f1_app.domain.model.Driver

interface DriverDetailsUseCase {
    suspend fun execute(driverId: String): Resource<Driver>
}