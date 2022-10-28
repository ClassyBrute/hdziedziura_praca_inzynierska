package com.example.f1_app.domain.use_case.next_race

import com.example.f1_app.common.Resource
import com.example.f1_app.data.toNextRace
import com.example.f1_app.domain.model.NextRace
import com.example.f1_app.domain.repository.HomeRepository
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import javax.inject.Inject

class RacesSeasonUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
) : RacesSeasonUseCase {

    override suspend fun execute(season: String): Resource<List<NextRace>> {
        return try {
            coroutineScope {
                val result = homeRepository.racesSeason(season).body()
                Resource.Success(
                    result?.toNextRace() ?: emptyList()
                )
            }
        } catch (e: IOException) {
            Resource.Error("Connection error", listOf(NextRace()))
        }
    }
}