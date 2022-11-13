package com.example.f1_app.data

import com.example.f1_app.data.network.dto.ConstructorStandingsDto

fun ConstructorStandingsDto.toConstructorStats(): Map<String, String> {
    var wcc = 0
    var wins = 0
    MRData.StandingsTable.StandingsLists.forEach {
        wins += it.ConstructorStandings[0].wins.toInt()
        if (it.ConstructorStandings[0].position == "1") wcc++
    }
    return mutableMapOf(
        "wcc" to wcc.toString(),
        "wins" to wins.toString()
    )
}