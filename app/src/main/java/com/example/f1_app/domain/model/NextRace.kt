package com.example.f1_app.domain.model

import com.example.f1_app.R
import com.example.f1_app.data.mocks.mockFlags

data class NextRace(
    val round: String = "",
    val name: String = "",
    val dateFrom: String = "",
    val dateTo: String = "",
    val image: Int? = mockFlags[name] ?: R.drawable.ic_f1,
    val raceName: String = ""
)