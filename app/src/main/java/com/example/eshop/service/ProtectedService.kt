package com.example.eshop.service

import com.example.eshop.data.categories.CategoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProtectedService {

    @GET("user/product")
    suspend fun product(): String

    @GET("user/category")
    suspend fun getAllCategories(): CategoryResponse

}