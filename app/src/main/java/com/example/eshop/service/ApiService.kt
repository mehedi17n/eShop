package com.example.eshop.service

import com.example.eshop.data.register.RegisterRequest
import com.example.eshop.data.register.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("user/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>
}