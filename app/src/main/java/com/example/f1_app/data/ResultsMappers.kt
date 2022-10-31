package com.example.f1_app.data

import com.example.f1_app.data.network.dto.LatestResultsDto
import com.example.f1_app.domain.model.Results

fun LatestResultsDto.toResults() =
    MRData.RaceTable.Races[0].Results.map {
        it.toResults(MRData.RaceTable.Races[0].raceName, MRData.RaceTable.round)
    }

fun LatestResultsDto.ResultDto.toResults(name: String, round: String) = Results(
    Driver.driverId,
    name,
    round,
    number,
    position,
    points,
    Driver.givenName,
    Driver.familyName,
    Constructor.name,
    grid,
    laps,
    status,
    Time.time,
    FastestLap.Time.time,
)
