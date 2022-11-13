package com.example.f1_app.domain.model

import com.example.f1_app.R
import com.example.f1_app.data.mocks.mockTeams

data class ConstructorStandings (
    val id: String = "",
    val position: String = "",
    val constructorName: String = "",
    val points: String = "",
    val wins: String = "",
    val color: Int? = mockTeams[constructorName] ?: R.color.grey
)