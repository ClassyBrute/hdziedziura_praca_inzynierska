package com.example.f1_app.domain.model

import com.example.f1_app.R
import com.example.f1_app.data.mocks.mockDrivers

data class Results(
    val driverId: String = "",
    val raceName: String = "",
    val round: String = "",
    val number: String = "",
    val position: String = "",
    val points: String = "",
    val driverName: String = "",
    val driverSurname: String = "",
    val constructor: String = "",
    val grid: String = "",
    val laps: String = "",
    val status: String = "",
    val time: String = "",
    val fastestLap: String = "",
    val image: Int? = mockDrivers[driverName] ?: R.drawable.ic_f1
)

