package com.example.f1_app.data.network.dto

data class ConstructorStandingsDto (
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
        val ConstructorStandings: List<ConstructorStandingsDto1> = listOf()
    )

    data class ConstructorStandingsDto1(
        val position: String = "",
        val positionText: String = "",
        val points: String = "",
        val wins: String = "",
        val Constructor: LatestResultsDto.ConstructorDto = LatestResultsDto.ConstructorDto()
    )
}
