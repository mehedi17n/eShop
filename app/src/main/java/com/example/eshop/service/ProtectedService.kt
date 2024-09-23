package com.example.eshop.service

import com.example.eshop.data.auth.logout.LogoutRequest
import com.example.eshop.data.auth.logout.LogoutResponse
import com.example.eshop.data.categories.CategoryResponse
import com.example.eshop.data.products.ProductResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProtectedService {

    @POST("user/logout")
    suspend fun logout(@Body logoutRequest: LogoutRequest): LogoutResponse


    @GET("user/product")
    suspend fun product(): ProductResponse

    @GET("user/category")
    suspend fun getCategories(): CategoryResponse

}