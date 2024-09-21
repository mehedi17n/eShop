package com.example.eshop.repository

import com.example.eshop.api.Resource
import com.example.eshop.data.categories.CategoryResponse
import com.example.eshop.service.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductRepo(private val api: AuthService) {

    suspend fun getCategories(): Flow<Resource<CategoryResponse>> = flow {
        try {
            emit(Resource.Loading)
            val response = api.getCategories()
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error"))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getProducts(): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.product() // Make the network request
//            Log.d("Repository createRegistration ", response.toString())
//            println("Repository createRegistration  $response")
            emit(Resource.Success(response))

        } catch (e: Exception) {
            println("Repository createRegistration  error")
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)
}