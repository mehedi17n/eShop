package com.example.eshop.repository

import android.util.Log
import com.example.eshop.api.Resource
import com.example.eshop.data.RegisterRequest
import com.example.eshop.data.RegisterResponse
import com.example.eshop.service.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class UserRepository(private val apiService: ApiService) {

    fun registerUser(request: RegisterRequest): Flow<Resource<RegisterResponse>> = flow {
        try {
            emit(Resource.Loading()) // Emit loading state
            val response = apiService.registerUser(request) // Make the network request
            Log.d("Repository", response.toString())

            if (response.isSuccessful && response.body()?.status == 200) {
                emit(Resource.Success(response.body()!!))
            } else if (response.body()?.status == 422) {
                emit(Resource.Error("Phone Number already exists. Please try another Phone Number."))
            } else {
                emit(Resource.Error(response.body()?.message ?: "An unexpected error occurred"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)
}