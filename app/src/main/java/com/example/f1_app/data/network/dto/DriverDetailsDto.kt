package com.example.f1_app.data.network.dto

data class DriverDetailsDto (
    val MRData: MRDataDto = MRDataDto()
) {
    data class MRDataDto (
        val xmlns: String = "",
        val series: String = "",
        val url: String = "",
        val limit: String = "",
        val offset: String = "",
        val total: String = "",
        val DriverTable: TableDto = TableDto()
    )

    data class TableDto (
        val season: String = "",
        val driverId: String = "",
        val Drivers: List<DriverDto> = listOf()
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
}






