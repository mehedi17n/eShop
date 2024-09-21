package com.example.eshop.repository

import android.util.Log
import com.example.eshop.api.Resource
import com.example.eshop.service.ProtectedService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductRepo(private val api: ProtectedService) {

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