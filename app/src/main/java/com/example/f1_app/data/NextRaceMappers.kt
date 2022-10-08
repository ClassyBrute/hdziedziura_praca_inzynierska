package com.example.f1_app.data

import com.example.f1_app.data.network.dto.NextRacesDto
import com.example.f1_app.domain.model.NextRace

fun NextRacesDto.toNextRace(): MutableList<NextRace> {
    val nextRace: MutableList<NextRace> = mutableListOf()
    MRData.RaceTable.Races.forEach {
        nextRace.add(
            NextRace(
                it.round,
                it.Circuit.Location.country,
                it.FirstPractice.date,
                it.date
            )
        )
    }
    return nextRace
}