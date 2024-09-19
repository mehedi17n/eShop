package com.example.eshop.repository

import android.util.Log
import com.example.eshop.api.Resource
import com.example.eshop.data.login.LoginRequest
import com.example.eshop.data.login.LoginResponse
import com.example.eshop.data.register.RegisterRequest
import com.example.eshop.data.register.RegisterResponse
import com.example.eshop.service.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(private val apiService: ApiService) {

    fun registerUser(request: RegisterRequest): Flow<Resource<RegisterResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.registerUser(request)
            Log.d("Repository", response.toString())

            emit(Resource.Success(response))

        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }.flowOn(Dispatchers.IO)


    fun loginUser(request: LoginRequest): Flow<Resource<LoginResponse>> = flow {
        try {
            emit(Resource.Loading())

            val response = apiService.loginUser(request)
            Log.d("Repository", response.toString())

            emit(Resource.Success(response))

        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}
