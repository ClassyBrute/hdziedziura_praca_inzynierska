package com.example.f1_app.domain.use_case.standings

import com.example.f1_app.common.Resource
import com.example.f1_app.data.toDriverStandings
import com.example.f1_app.domain.model.DriverStandings
import com.example.f1_app.domain.repository.HomeRepository
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import javax.inject.Inject

class DriverStandingsUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
) : DriverStandingsUseCase {

    override suspend fun execute(): Resource<List<DriverStandings>> {
        return try {
            coroutineScope {
                val result = homeRepository.driverStandings().body()
                Resource.Success(
                    result?.toDriverStandings() ?: emptyList()
                )
            }
        } catch (e: IOException) {
            println("error")
            Resource.Error("Connection error", listOf(DriverStandings()))
        }
    }
}