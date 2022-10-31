package com.example.f1_app.data

import com.example.f1_app.data.network.dto.RacesAllTimeDto
import com.example.f1_app.domain.model.RacesAllTime

fun RacesAllTimeDto.toRacesAllTime(): String {
    var races = 0
    MRData.StatusTable.Status.forEach {
        races += it.count.toInt()
    }
    return races.toString()
}
