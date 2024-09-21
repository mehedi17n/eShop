package com.example.eshop.service


import com.example.eshop.data.auth.login.LoginRequest
import com.example.eshop.data.auth.login.LoginResponse
import com.example.eshop.data.auth.register.CreateRegistration
import com.example.eshop.data.auth.register.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("user/register")
    suspend fun createAccount(
        @Body body: CreateRegistration
    ): RegistrationResponse

    @POST("user/login")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse

}