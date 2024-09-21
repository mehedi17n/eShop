package com.example.eshop.repository

import android.util.Log
import com.example.eshop.api.Resource
import com.example.eshop.data.auth.login.LoginRequest
import com.example.eshop.data.auth.login.LoginResponse
import com.example.eshop.data.auth.register.CreateRegistration
import com.example.eshop.data.auth.register.RegistrationResponse
import com.example.eshop.service.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepo(private val api: AuthService) {

    suspend fun createRegistration(
        user: CreateRegistration

    ): Flow<Resource<RegistrationResponse>> = flow {
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.createAccount(user) // Make the network request
//            Log.d("Repository createRegistration ", response.toString())
//            println("Repository createRegistration  $response")
            emit(Resource.Success(response))

        } catch (e: Exception) {
            println("Repository createRegistration  error")
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)

    suspend fun login(
        user: LoginRequest
    ): Flow<Resource<LoginResponse>> = flow {
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.login(user) // Make the network request
            Log.d("Repository", response.toString())
            emit(Resource.Success(response))

        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
            Log.d("Error", e.toString())
        }
    }.flowOn(Dispatchers.IO)

}