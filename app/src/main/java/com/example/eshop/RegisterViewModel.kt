package com.example.eshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshop.api.Resource
import com.example.eshop.data.RegisterRequest
import com.example.eshop.data.RegisterResponse
import com.example.eshop.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    // Exposed StateFlow for observing the registration response
    private val _registerResponse = MutableStateFlow<Resource<RegisterResponse>>(Resource.Loading())
    val registerResponse: StateFlow<Resource<RegisterResponse>> get() = _registerResponse

    fun registerUser(request: RegisterRequest) {
        viewModelScope.launch {
            repository.registerUser(request).collect { result ->
                _registerResponse.value = result
            }
        }
    }
}