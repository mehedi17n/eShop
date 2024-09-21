package com.example.eshop.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshop.api.GuestApiClient
import com.example.eshop.api.Resource
import com.example.eshop.data.auth.login.LoginRequest
import com.example.eshop.data.auth.login.LoginResponse
import com.example.eshop.datasource.DataStoreKeys
import com.example.eshop.datasource.DatastoreManager
import com.example.eshop.repository.AuthRepo
import com.example.eshop.utils.Utility.isValidEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    // Variables
    val emailStateFlow = MutableStateFlow<String>("")
    val passwordStateFlow = MutableStateFlow<String>("")


    //API Response
    val loginResponse = MutableStateFlow<LoginResponse?>(null)
    val isLoading = MutableStateFlow(true)
    val errorMessage = MutableStateFlow<String?>(null)

    //DI
    private val authRepo = AuthRepo(GuestApiClient.api)
    private val dataStoreManager = DatastoreManager(application)


    // Update email
    fun onEmailChanged(email: String) {
        emailStateFlow.value = email
    }

    // Update password
    fun onPasswordChanged(password: String) {
        passwordStateFlow.value = password
    }

    fun validateForm(): Boolean {
        val email = emailStateFlow.value
        val password = passwordStateFlow.value


        var isValid = true

        // Validate email
        if (email.isEmpty() || !isValidEmail(email)) {
//            userEmail.error = "Please enter a valid email address"
            isValid = false
        } else {
//            userEmail.error = null
        }

        // Validate password
        if (password.isEmpty() || password.length < 6) {
//            userPassword.error = "Password must be at least 6 characters"
            isValid = false
        } else {
//            userPassword.error = null
        }

        // If all fields are valid, show success message
        if (isValid) {
//            navigateToHome()
//            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
            // Clear the form fields
//            userEmail.editText?.text?.clear()
//            userPassword.editText?.text?.clear()
            return true

        } else {
//            Toast.makeText(this, "Please fill in all fields correctly!", Toast.LENGTH_SHORT).show()
            return false

        }

    }


    fun onCLickLogin(context: Context) {
        if (validateForm()) {

            Log.d("Viewmodel"," IF")
            val loginInstance = LoginRequest(
                user_id = emailStateFlow.value,
                password = passwordStateFlow.value
            )
            Log.d("Viewmodel",loginInstance.toString())
            viewModelScope.launch {
                authRepo.login(
                    loginInstance
                ).collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            isLoading.value = true // Set loading state
                        }

                        is Resource.Success -> {
                            isLoading.value = false // Stop loading
                            loginResponse.value = resource.data // Set news data
                            Log.d("Viewmodel", resource.data.toString())
                            resource.data.token?.let {
                                dataStoreManager.saveString(DataStoreKeys.token, it)
                            }
                        }

                        is Resource.Error -> {
                            isLoading.value = false // Stop loading
                            errorMessage.value = resource.message // Set error message
                        }
                    }
                }
            }
        } else {

            Log.d("Viewmodel"," else")
            Toast.makeText(context, "Enter Valid Data", Toast.LENGTH_SHORT).show()
        }
    }

}



