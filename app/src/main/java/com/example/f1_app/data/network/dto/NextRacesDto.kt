package com.example.f1_app.data.network.dto

data class NextRacesDto(
    val MRData: MRDataDto = MRDataDto()
) {
    data class MRDataDto (
        val xmlns: String = "",
        val series: String = "",
        val url: String = "",
        val limit: String = "",
        val offset: String = "",
        val total: String = "",
        val RaceTable: RaceTableDto = RaceTableDto()
    )

    data class RaceTableDto (
        val season: String = "",
        val Races: List<RaceDto> = listOf()
    )

    data class RaceDto (
        val season: String = "",
        val round: String = "",
        val url: String = "",
        val raceName: String = "",
        val Circuit: LatestResultsDto.CircuitDto = LatestResultsDto.CircuitDto(),
        val date: String = "",
        val time: String = "",
        val FirstPractice: SessionInfoDto = SessionInfoDto(),
        val SecondPractice: SessionInfoDto = SessionInfoDto(),
        val ThirdPractice: SessionInfoDto = SessionInfoDto(),
        val Sprint: SessionInfoDto = SessionInfoDto()
    )

    data class SessionInfoDto (
        val date: String = "",
        val time: String = ""
    )
}