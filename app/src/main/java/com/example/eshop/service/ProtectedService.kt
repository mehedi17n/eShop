package com.example.eshop.service

import com.example.eshop.data.categories.CategoryResponse
import com.example.eshop.data.products.ProductResponse
import retrofit2.http.GET

interface ProtectedService {

    @GET("user/product")
    suspend fun product(): ProductResponse

    @GET("user/category")
    suspend fun getCategories(): CategoryResponse

}