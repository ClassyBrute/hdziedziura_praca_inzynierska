package com.example.f1_app.data

import com.example.f1_app.data.network.dto.LatestResultsDto
import com.example.f1_app.domain.model.Results

fun LatestResultsDto.toResults(): MutableList<Results> {
    val result: MutableList<Results> = mutableListOf()
    MRData.RaceTable.Races[0].Results.forEach {
        result.add(
            Results(
                MRData.RaceTable.Races[0].raceName,
                it.number,
                it.position,
                it.points,
                it.Driver.givenName,
                it.Driver.familyName,
                it.Constructor.name,
                it.grid,
                it.laps,
                it.status,
                it.Time.time,
                it.FastestLap.Time.time,
            )
        )
    }
    return result
}
