package com.example.f1_app.domain.use_case.next_race

import com.example.f1_app.common.Resource
import com.example.f1_app.data.toNextRace
import com.example.f1_app.domain.model.NextRace
import com.example.f1_app.domain.repository.NextRaceRepository
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import javax.inject.Inject

class NextRaceUseCaseImpl @Inject constructor(
    private val nextRaceRepository: NextRaceRepository
) : NextRaceUseCase {

    override suspend fun execute(): Resource<MutableList<NextRace>> {
        return try {
            coroutineScope {
                val result = nextRaceRepository.nextRace().body()
                Resource.Success(
                    result?.toNextRace() ?: mutableListOf(NextRace())
                )
            }
        } catch (e: IOException) {
            Resource.Error("Connection error")
        }
    }
}