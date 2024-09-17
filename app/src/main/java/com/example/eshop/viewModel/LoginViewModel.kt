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

    private val _loginResponse = MutableStateFlow<Resource<LoginResponse>>(Resource.Loading())
    val loginResponse: StateFlow<Resource<LoginResponse>> get() = _loginResponse

    fun loginUser(request: LoginRequest) {
        viewModelScope.launch {
            userRepository.loginUser(request).collect { result ->
                _loginResponse.value = result
            }
        }
    }
}