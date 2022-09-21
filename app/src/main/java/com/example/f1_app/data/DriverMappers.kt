package com.example.f1_app.data

import com.example.f1_app.data.network.dto.DriverDetailsDto
import com.example.f1_app.domain.model.Driver

fun DriverDetailsDto.toDriver() =
    Driver(
        MRData.DriverTable.Drivers[0].driverId,
        MRData.DriverTable.Drivers[0].permanentNumber,
        MRData.DriverTable.Drivers[0].code,
        MRData.DriverTable.Drivers[0].url,
        MRData.DriverTable.Drivers[0].givenName,
        MRData.DriverTable.Drivers[0].familyName,
        MRData.DriverTable.Drivers[0].dateOfBirth,
        MRData.DriverTable.Drivers[0].nationality
    )
