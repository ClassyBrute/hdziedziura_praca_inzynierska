package com.example.f1_app.data.network.dto

data class LatestResultsDto(
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
        val round: String = "",
        val Races: List<RaceDto> = listOf()
    )

    data class RaceDto (
        val season: String = "",
        val round: String = "",
        val url: String = "",
        val raceName: String = "",
        val Circuit: CircuitDto = CircuitDto(),
        val date: String = "",
        val time: String = "",
        val Results: List<ResultDto> = listOf()
    )

    data class CircuitDto (
        val circuitID: String = "",
        val url: String = "",
        val circuitName: String = "",
        val Location: LocationDto = LocationDto()
    )

    data class LocationDto (
        val lat: String = "",
        val long: String = "",
        val locality: String = "",
        val country: String = ""
    )

    data class ResultDto (
        val number: String = "",
        val position: String = "",
        val positionText: String = "",
        val points: String = "",
        val Driver: DriverDto = DriverDto(),
        val Constructor: ConstructorDto = ConstructorDto(),
        val grid: String = "",
        val laps: String = "",
        val status: String = "",
        val Time: ResultTimeDto = ResultTimeDto(),
        val FastestLap: FastestLapDto = FastestLapDto()
    )

    data class DriverDto (
        val driverId: String = "",
        val permanentNumber: String = "",
        val code: String = "",
        val url: String = "",
        val givenName: String = "",
        val familyName: String = "",
        val dateOfBirth: String = "",
        val nationality: String = ""
    )

    data class ConstructorDto (
        val constructorID: String = "",
        val url: String = "",
        val name: String = "",
        val nationality: String = ""
    )

    data class FastestLapDto (
        val rank: String = "",
        val lap: String = "",
        val Time: FastestLapTimeDto = FastestLapTimeDto(),
        val AverageSpeed: AverageSpeedDto = AverageSpeedDto()
    )

    data class AverageSpeedDto (
        val units: String = "",
        val speed: String = ""
    )

    data class FastestLapTimeDto (
        val time: String = ""
    )

    data class ResultTimeDto (
        val millis: String = "",
        val time: String = ""
    )
}