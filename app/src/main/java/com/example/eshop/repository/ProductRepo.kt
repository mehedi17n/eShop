package com.example.eshop.repository

import android.util.Log
import com.example.eshop.api.Resource
import com.example.eshop.data.products.ProductResponse
import com.example.eshop.service.ProtectedService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductRepo(private val api: ProtectedService) {

    suspend fun getProducts(): Flow<Resource<ProductResponse>> = flow {
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.product() // Make the network request
            Log.d("Repository getProducts ", response.toString())
            emit(Resource.Success(response))

        } catch (e: Exception) {
            Log.d("Repository getProducts error ", e.toString())

            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)
//    suspend fun getCategories(): Flow<Resource<CategoryResponse>> = flow {
//        try {
//            emit(Resource.Loading)
//            val response = api.getCategories()
//            Log.d("Repository getCategories ", response.toString())
//            println("Repository getCategories  $response")
//            emit(Resource.Success(response))
//        } catch (e: Exception) {
//            emit(Resource.Error(e.message ?: "Unknown Error"))
//        }
//    }.flowOn(Dispatchers.IO)



}
