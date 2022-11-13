package com.example.f1_app.data

import com.example.f1_app.data.network.dto.SeasonsDto
import com.example.f1_app.domain.model.ConstructorRace

fun SeasonsDto.toConstructorRace() =
    ConstructorRace(MRData.SeasonTable.Seasons[0].season)