package com.example.f1_app.data.network.dto

data class ConstructorDetailsDto(
    val MRData: MRDataDto = MRDataDto()
) {
    data class MRDataDto(
        val xmlns: String = "",
        val series: String = "",
        val url: String = "",
        val limit: String = "",
        val offset: String = "",
        val total: String = "",
        val ConstructorTable: ConstructorTableDto = ConstructorTableDto()
    )

    data class ConstructorTableDto(
        val constructorId: String = "",
        val Constructors: List<LatestResultsDto.ConstructorDto> = listOf()
    )
}
