package com.example.f1_app.data

import com.example.f1_app.data.network.dto.NextRacesDto
import com.example.f1_app.domain.model.NextRace

fun NextRacesDto.toNextRace() =
    MRData.RaceTable.Races.map {
        NextRace(
            it.round,
            it.Circuit.Location.country,
            it.FirstPractice.date,
            it.date
        )
    }