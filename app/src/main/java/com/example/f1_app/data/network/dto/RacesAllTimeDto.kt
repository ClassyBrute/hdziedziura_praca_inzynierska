package com.example.f1_app.data.network.dto

data class RacesAllTimeDto(
    val MRData: MRDataDto = MRDataDto()
) {
    data class MRDataDto(
        val xmlns: String = "",
        val series: String = "",
        val url: String = "",
        val limit: String = "",
        val offset: String = "",
        val total: String = "",
        val StatusTable: StatusTableDto = StatusTableDto()
    )

    data class StatusTableDto(
        val driverId: String = "",
        val Status: List<StatusDto> = listOf()
    )

    data class StatusDto(
        val statusId: String = "",
        val count: String = "",
        val status: String = ""
    )
}