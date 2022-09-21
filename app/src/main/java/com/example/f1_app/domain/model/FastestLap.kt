package com.example.f1_app.domain.model

data class FastestLap(
    val rank: String = "",
    val lap: String = "",
    val time: Time = Time(),
    val averageSpeed: AverageSpeed = AverageSpeed()
)
