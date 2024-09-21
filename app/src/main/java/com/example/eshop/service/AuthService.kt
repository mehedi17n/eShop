package com.example.eshop.service


import com.example.eshop.data.auth.login.LoginRequest
import com.example.eshop.data.auth.login.LoginResponse
import com.example.eshop.data.auth.register.CreateRegistration
import com.example.eshop.data.auth.register.RegistrationResponse
import com.example.eshop.data.categories.CategoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
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

    @GET("user/category")
    suspend fun getCategories(): CategoryResponse

    @GET("user/product")
    suspend fun product(): String

}