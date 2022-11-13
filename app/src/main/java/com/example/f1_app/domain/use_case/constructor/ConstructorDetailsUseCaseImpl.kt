package com.example.f1_app.domain.use_case.constructor

import com.example.f1_app.common.Resource
import com.example.f1_app.data.toConstructor
import com.example.f1_app.domain.model.Constructor
import com.example.f1_app.domain.repository.HomeRepository
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import javax.inject.Inject

class ConstructorDetailsUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
) : ConstructorDetailsUseCase {

    override suspend fun execute(constructorId: String): Resource<Constructor> {
        return try {
            coroutineScope {
                val result = homeRepository.constructorDetails(constructorId).body()
                Resource.Success(
                    result?.toConstructor() ?: Constructor()
                )
            }
        } catch (e: IOException) {
            Resource.Error("Connection error")
        }
    }
}