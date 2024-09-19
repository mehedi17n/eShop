package com.example.eshop.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshop.api.Resource
import com.example.eshop.data.login.LoginRequest
import com.example.eshop.data.login.LoginResponse
import com.example.eshop.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    // State to hold login response data and loading/error states
    private val _loginResponse = MutableStateFlow<Resource<LoginResponse>>(Resource.Loading())
    val loginResponse: StateFlow<Resource<LoginResponse>> get() = _loginResponse

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun loginUser(request: LoginRequest) {
        viewModelScope.launch {
            userRepository.loginUser(request).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        _isLoading.value = false // Stop loading
                        _loginResponse.value = resource // Set login response data
                        // Optionally log the response
                        // Log.d("LoginViewModel", resource.data.toString())
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