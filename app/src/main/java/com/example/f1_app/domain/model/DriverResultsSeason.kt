package com.example.f1_app.domain.model

import com.example.f1_app.R
import com.example.f1_app.data.mocks.mockFlags
import com.example.f1_app.data.mocks.mockMaps

data class DriverResultsSeason(
    val round: String = "",
    val driverId: String = "",
    val season: String = "",
    val country: String = "",
    val constructor: String = "",
    val position: String = "",
    val points: String = "",
    val name: String = "",
    val raceCountry: String = "",
    val raceName: String = "",
    val circuitName: String = "",
    val image: Int? = mockFlags[country] ?: R.drawable.ic_f1,
    val map: Int? = mockMaps[country] ?: R.drawable.ic_f1
)