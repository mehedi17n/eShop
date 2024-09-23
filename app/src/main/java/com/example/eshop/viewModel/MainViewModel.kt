package com.example.eshop.viewModel

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshop.datasource.DatastoreManager
import com.example.eshop.api.GuestApiClient
import com.example.eshop.api.Resource
import com.example.eshop.data.auth.login.LoginRequest
import com.example.eshop.data.auth.login.LoginResponse
import com.example.eshop.data.auth.register.CreateRegistration
import com.example.eshop.data.auth.register.RegistrationResponse
import com.example.eshop.repository.AuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

  }



