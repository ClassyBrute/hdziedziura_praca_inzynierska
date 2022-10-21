package com.example.f1_app.domain.model

import com.example.f1_app.R
import com.example.f1_app.data.mocks.mockDrivers

data class DriverStandings(
    val position: String = "",
    val points: String = "",
    val wins: String = "",
    val driverName: String = "",
    val driverSurname: String = "",
    val constructorName: String = "",
    val image: Int? = mockDrivers[driverName] ?: R.drawable.ic_f1
)