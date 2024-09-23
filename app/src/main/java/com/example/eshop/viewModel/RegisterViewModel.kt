package com.example.eshop.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshop.api.GuestApiClient
import com.example.eshop.api.Resource
import com.example.eshop.data.auth.register.CreateRegistration
import com.example.eshop.data.auth.register.RegistrationResponse
import com.example.eshop.repository.AuthRepo
import com.example.eshop.utils.Utility
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    // Variables
    val nameStateFlow = MutableStateFlow<String>("")
    val emailStateFlow = MutableStateFlow<String>("")
    val phoneStateFlow = MutableStateFlow<String>("")
    val passwordStateFlow = MutableStateFlow<String>("")

    //API Response
    val registrationResponse = MutableStateFlow<RegistrationResponse?>(null)
    val isLoading = MutableStateFlow(true)
    val errorMessage = MutableStateFlow(false)

    //Client Repo
    private val authRepo = AuthRepo(GuestApiClient.api)

   // Update name
    fun onNameChanged(name: String){
        nameStateFlow.value = name
    }
    // Update email
    fun onEmailChanged(email: String) {
        emailStateFlow.value = email
    }
    // Update
    fun onPhoneNoChanged(phone: String) {
        phoneStateFlow.value = phone
    }
    // Update password
    fun onPasswordChanged(password: String) {
        passwordStateFlow.value = password
    }


//    Validation Function
private fun validateForm(): Boolean {
    val userName = nameStateFlow.value
    val email = emailStateFlow.value
    val phoneNumber = phoneStateFlow.value
    val password = passwordStateFlow.value

    var isValid = true

    // Validate username
    if (userName.isEmpty()) {
        isValid = false
    } else {
//        userName.error = null
    }

    // Validate email
    if (email.isEmpty() || !isValidEmail(email)) {
        isValid = false
    } else {
//        userEmail.error = null
    }

    // Validate phone number
    if (phoneNumber.isEmpty() || phoneNumber.length != 10 || phoneNumber[0] != '1') {
        isValid = false
    } else {
//        phoneNumber.error = null
    }

    // Validate password
    if (password.isEmpty() || password.length < 6) {
        isValid = false
    } else {
//        userPassword.error = null
    }


    // If all fields are valid, show success message
    if (isValid) {
        return true
    } else
//        Toast.makeText(this, "Please fill in all fields correctly!", Toast.LENGTH_SHORT).show()
        return false
}


    private fun isValidEmail(email: String): Boolean {
        return  Utility.isValidEmail(email)
    }

    fun onClickRegister(context: Context) {
        if (validateForm()) {
            val newInstance = CreateRegistration(
                name = nameStateFlow.value,
                phone = phoneStateFlow.value,
                email = emailStateFlow.value,
                password = passwordStateFlow.value
            )

            viewModelScope.launch {
                authRepo.createRegistration(
                    newInstance
                ).collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            isLoading.value = true // Set loading state
                        }

                        is Resource.Success -> {
                            isLoading.value = false // Stop loading
                            registrationResponse.value = resource.data // Set news data
                            Log.d("Viewmodel createRegistration", resource.data.toString())
                        }

                        is Resource.Error ->{
                            isLoading.value = false // Stop loading
                            errorMessage.value = true // Set error message
                        }
                    }
                }
            }
        }else{
            Log.d("Viewmodel", " else")
            Toast.makeText(context, "Enter Valid Data", Toast.LENGTH_SHORT).show()
        }
    }
}

