package com.example.f1_app.data.network.dto

data class DriverStandingsDto (
    val MRData: MRDataDto = MRDataDto()
) {
    data class MRDataDto(
        val xmlns: String = "",
        val series: String = "",
        val url: String = "",
        val limit: String = "",
        val offset: String = "",
        val total: String = "",
        val StandingsTable: StandingsTableDto = StandingsTableDto()
    )

    data class StandingsTableDto(
        val season: String = "",
        val StandingsLists: List<StandingsListsDto> = listOf()
    )

    data class StandingsListsDto(
        val season: String = "",
        val round: String = "",
        val DriverStandings: List<DriverStandingsDto1> = listOf()
    )

    data class DriverStandingsDto1(
        val position: String = "",
        val positionText: String = "",
        val points: String = "",
        val wins: String = "",
        val Driver: LatestResultsDto.DriverDto = LatestResultsDto.DriverDto(),
        val Constructors: List<LatestResultsDto.ConstructorDto> = listOf()
    )
}