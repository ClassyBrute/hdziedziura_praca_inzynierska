package com.example.f1_app.data.network.dto

data class SeasonsDto(
    val MRData: MRDataDto = MRDataDto()
) {
    data class MRDataDto(
        val xmlns: String = "",
        val series: String = "",
        val url: String = "",
        val limit: String = "",
        val offset: String = "",
        val total: String = "",
        val SeasonTable: SeasonTableDto = SeasonTableDto()
    )

    data class SeasonTableDto(
        val constructorId: String = "",
        val Seasons: List<SeasonDto> = listOf()
    )

    data class SeasonDto(
        val season: String = "",
        val url: String = ""
    )
}