package com.example.f1_app.data

import com.example.f1_app.data.network.dto.DriverStandingsDto

fun DriverStandingsDto.toStatsAllTime(): Map<String, String> {
    var points = 0f
    var wins = 0
    var wdc = 0
    MRData.StandingsTable.StandingsLists.forEach {
        points += it.DriverStandings[0].points.toFloat()
        wins += it.DriverStandings[0].wins.toInt()
        if (it.DriverStandings[0].position == "1") wdc++
    }
    return mutableMapOf(
        "points" to points.toString(),
        "wins" to wins.toString(),
        "wdc" to wdc.toString()
    )
}