package com.example.f1_app.presentation.homeRvItems

data class RaceItem(
    val round: String = "",
    val country: String = "",
    val dateFrom: String = "",
    val dateTo: String = "",
    val image: Int? = 0,
    val raceName: String = "",
    val circuitName: String = "",
    val map: Int? = 0
)