package com.example.f1_app.domain.use_case.constructor

import com.example.f1_app.common.Resource
import com.example.f1_app.data.toConstructorRace
import com.example.f1_app.domain.model.ConstructorRace
import com.example.f1_app.domain.repository.HomeRepository
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import javax.inject.Inject

class ConstructorFirstRaceUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
) : ConstructorFirstRaceUseCase {

    override suspend fun execute(constructorId: String): Resource<ConstructorRace> {
        return try {
            coroutineScope {
                val result = homeRepository.firstRace(constructorId).body()
                Resource.Success(
                    result?.toConstructorRace() ?: ConstructorRace()
                )
            }
        } catch (e: IOException) {
            Resource.Error("Connection error")
        }
    }
}