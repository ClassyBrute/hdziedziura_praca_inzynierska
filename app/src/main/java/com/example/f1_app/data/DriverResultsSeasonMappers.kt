package com.example.f1_app.data

import com.example.f1_app.data.network.dto.LatestResultsDto
import com.example.f1_app.domain.model.DriverResultsSeason

fun LatestResultsDto.toResultsSeason() =
    MRData.RaceTable.Races.map {
        DriverResultsSeason(
            it.round,
            it.Results[0].Driver.driverId,
            it.season,
            it.Circuit.Location.country,
            it.Results[0].Constructor.name,
            it.Results[0].position,
            it.Results[0].points,
            it.Results[0].Driver.givenName+" "+it.Results[0].Driver.familyName
        )
    }
