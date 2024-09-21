package com.example.eshop.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshop.api.GuestApiClient
import com.example.eshop.api.Resource
import com.example.eshop.data.categories.Data
import com.example.eshop.repository.ProductRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    val categories = MutableStateFlow<List<Data>>(emptyList())
    val isLoading = MutableStateFlow(true)
    val errorMessage = MutableStateFlow<String?>(null)

    private val repository = ProductRepo(GuestApiClient.api)

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            repository.getCategories().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                    is Resource.Success -> {
                        isLoading.value = false
                        categories.value = resource.data.data?.filterNotNull() ?: emptyList()
                    }
                    is Resource.Error -> {
                        isLoading.value = false
                        errorMessage.value = resource.message
                    }
                }
            }
        }
    }
}
