package com.example.eshop.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshop.api.Resource
import com.example.eshop.data.register.RegisterRequest
import com.example.eshop.data.register.RegisterResponse
import com.example.eshop.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    // State to hold registration response data and loading/error states
    private val _registerResponse = MutableStateFlow<Resource<RegisterResponse>>(Resource.Loading())
    val registerResponse: StateFlow<Resource<RegisterResponse>> get() = _registerResponse

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun registerUser(request: RegisterRequest) {
        viewModelScope.launch {
            repository.registerUser(request).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        _isLoading.value = false // Stop loading
                        _registerResponse.value = resource // Set registration response data
                    }

                    is Resource.Error -> {
                        _isLoading.value = false // Stop loading
                        _errorMessage.value = resource.message // Set error message
                    }
                }
            }
        }
    }
}
