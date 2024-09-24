package com.example.eshop.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshop.api.GuestApiClient
import com.example.eshop.api.ProtectedApiClient
import com.example.eshop.api.Resource
import com.example.eshop.data.categories.CategoryResponse
import com.example.eshop.data.categories.Data
import com.example.eshop.repository.ProductRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    //    Api Responses in state flow
    val categoryResponseFlow = MutableStateFlow<CategoryResponse?>(null)
    val isLoading = MutableStateFlow(true)
    val errorMessage = MutableStateFlow<String?>(null)

    //   Protected Repository added
    private val protectedRepo = ProductRepo(ProtectedApiClient.api)

    init {
        getCategories()
    }


    fun getCategories() {
        viewModelScope.launch {
            protectedRepo.getCategories().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        isLoading.value = false // Stop loading
                        categoryResponseFlow.value = resource.data // Set news data
                        Log.d("Viewmodel", resource.data.toString())
                    }

                    is Resource.Error -> {
                        isLoading.value = false // Stop loading
                        errorMessage.value = resource.message // Set error message
                    }
                }
            }
        }

    }


}