package com.example.f1_app.domain.use_case.standings

import com.example.f1_app.common.Resource
import com.example.f1_app.data.toConstructorStandings
import com.example.f1_app.domain.model.ConstructorStandings
import com.example.f1_app.domain.repository.HomeRepository
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import javax.inject.Inject

class ConstructorStandingsSeasonUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
) : ConstructorStandingsSeasonUseCase {

    override suspend fun execute(season: String): Resource<List<ConstructorStandings>> {
        return try {
            coroutineScope {
                val result = homeRepository.constructorStandingsSeason(season).body()
                Resource.Success(
                    result?.toConstructorStandings() ?: emptyList()
                )
            }
        } catch (e: IOException) {
            println("error")
            Resource.Error("Connection error", listOf(ConstructorStandings()))
        }
    }
}