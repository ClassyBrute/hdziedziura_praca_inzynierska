package com.example.f1_app.data

import com.example.f1_app.data.network.dto.DriverStandingsDto
import com.example.f1_app.domain.model.DriverStandings

fun DriverStandingsDto.toDriverStandings() =
    MRData.StandingsTable.StandingsLists[0].DriverStandings.map {
        DriverStandings(
            it.position,
            it.points,
            it.wins,
            it.Driver.givenName,
            it.Driver.familyName,
            it.Constructors[0].name
        )
    }