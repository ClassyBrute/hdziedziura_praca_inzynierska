package com.example.f1_app.data

import com.example.f1_app.data.network.dto.ConstructorStandingsDto
import com.example.f1_app.domain.model.ConstructorStandings

fun ConstructorStandingsDto.toConstructorStandings() =
    MRData.StandingsTable.StandingsLists[0].ConstructorStandings.map {
        ConstructorStandings(
            it.Constructor.constructorId,
            it.position,
            it.Constructor.name,
            it.points,
            it.wins
        )
    }