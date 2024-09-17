package com.example.eshop.registation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.eshop.R
import com.example.eshop.RegisterViewModel
import com.example.eshop.RegisterViewModelFactory
import com.example.eshop.api.ApiClient
import com.example.eshop.api.Resource
import com.example.eshop.data.RegisterRequest
import com.example.eshop.login.LoginActivity
import com.example.eshop.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Initialize ViewModel
        val apiService = ApiClient.apiService
        val userRepository = UserRepository(apiService)
        val viewModelFactory = RegisterViewModelFactory(userRepository)
        registerViewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)

        // Initialize Views
        nameEditText = findViewById(R.id.etName)
        phoneEditText = findViewById(R.id.etPhone)
        emailEditText = findViewById(R.id.etEmail)
        passwordEditText = findViewById(R.id.etPassword)
        registerButton = findViewById(R.id.loginButton)

        // Set onClickListener for register button
        registerButton.setOnClickListener {
            registerUser()
        }

        // Observe registration response
        lifecycleScope.launch {
            registerViewModel.registerResponse.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Show loading state if needed
                    }
                    is Resource.Success -> {
                        // Show success message and navigate to LoginActivity
                        Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish() // Finish RegisterActivity
                    }
                    is Resource.Error -> {
                        // Show error message
                        Toast.makeText(this@RegisterActivity, resource.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun registerUser() {
        val name = nameEditText.text.toString()
        val phone = phoneEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            val registerRequest = RegisterRequest(name, phone, email, password)
            registerViewModel.registerUser(registerRequest)
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}