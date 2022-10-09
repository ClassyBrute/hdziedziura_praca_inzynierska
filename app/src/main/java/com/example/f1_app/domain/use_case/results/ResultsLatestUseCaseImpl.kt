package com.example.f1_app.domain.use_case.results

import com.example.f1_app.common.Resource
import com.example.f1_app.data.toResults
import com.example.f1_app.domain.model.Results
import com.example.f1_app.domain.repository.ResultsRepository
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import javax.inject.Inject

class ResultsLatestUseCaseImpl @Inject constructor(
    private val resultsRepository: ResultsRepository
) : ResultsLatestUseCase {

    override suspend fun execute(): Resource<List<Results>> {
        return try {
            coroutineScope {
                val result = resultsRepository.latestResults().body()
                Resource.Success(
                    result?.toResults() ?: emptyList()
                )
            }
        } catch (e: IOException) {
            println("error")
            Resource.Error("Connection error", listOf(Results()))
        }
    }
}