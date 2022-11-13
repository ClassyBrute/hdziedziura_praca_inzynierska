package com.example.f1_app.data

import com.example.f1_app.data.network.dto.ConstructorDetailsDto
import com.example.f1_app.domain.model.Constructor

fun ConstructorDetailsDto.toConstructor() =
    Constructor(
        MRData.ConstructorTable.Constructors[0].constructorId,
        MRData.ConstructorTable.Constructors[0].url,
        MRData.ConstructorTable.Constructors[0].name,
        MRData.ConstructorTable.Constructors[0].nationality
    )