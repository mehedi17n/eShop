package com.example.eshop.api

import com.example.eshop.service.ApiService
import com.google.firebase.database.core.TokenProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://sample-ecom.parallaxlogic.dev/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}

//object TokenApiClient {
//
//    private val tokenProvider: TokenProvider = TokenProvider()
//
//    // OkHttpClient with AuthInterceptor to add the token in headers
//    private val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            val token = tokenProvider.getToken() // Get your token here
//            val newRequest = chain.request().newBuilder()
//                .addHeader("Authorization", "Bearer $token")
//                .build()
//            chain.proceed(newRequest)
//        }
//        .build()
//
//    // Retrofit instance with custom OkHttpClient
//    val api: ProductService by lazy {
//        Retrofit.Builder()
//            .baseUrl("https://sample-ecom.parallaxlogic.dev/api/")
//            .client(okHttpClient) // Add OkHttpClient to Retrofit
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ProductService::class.java)
//    }
//}