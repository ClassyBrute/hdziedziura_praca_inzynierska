package com.example.f1_app.presentation.homeRvItems

data class DriverItem(
    val name: String = "",
    val surname: String = "",
    val position: String = "",
    val team: String = "",
    var image: Int? = 0,
    val points: String = "",
    val wins: String = "",
    val positionStandings: String = ""
)
