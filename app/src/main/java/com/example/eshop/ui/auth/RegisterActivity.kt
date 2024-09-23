package com.example.eshop.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.eshop.R
import com.example.eshop.data.auth.register.RegistrationResponse
import com.example.eshop.utils.Utility.isValidEmail
import com.example.eshop.viewModel.MainViewModel
import com.example.eshop.viewModel.RegisterViewModel
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var userName: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var registerbtn: Button
    private lateinit var viewModel: RegisterViewModel
    private lateinit var optionLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        userName = findViewById(R.id.etName)
        phoneNumber = findViewById(R.id.etPhone)
        userEmail = findViewById(R.id.etEmail)
        userPassword = findViewById(R.id.etPassword)
        registerbtn = findViewById(R.id.registerButton)
        optionLogin = findViewById(R.id.tvSignin)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        // Set up text watchers for email and password input
        setupTextWatchers()


        // Button clicked listener for Register
        registerbtn.setOnClickListener {
            viewModel.onClickRegister(this)
            // Observe StateFlows for changes
            observeViewModel()
        }
    }

    //    Implementations for rotate screen to set values
    override fun onStart() {
        super.onStart()
        // Set initial text for the text fields
        userName.setText(viewModel.nameStateFlow.value)
        userEmail.setText(viewModel.emailStateFlow.value)
        phoneNumber.setText(viewModel.phoneStateFlow.value)
        userPassword.setText(viewModel.passwordStateFlow.value)
    }

    private fun setupTextWatchers() {

        // UserName text watcher
        userName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onNameChanged(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Email text watcher
        userEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEmailChanged(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // PhoneNO. text watcher
        phoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPhoneNoChanged(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Password text watcher
        userPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPasswordChanged(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

    }

    private fun observeViewModel() {
        // Get Registration Responses
        lifecycleScope.launch {
            viewModel.registrationResponse.collect { response ->
                if (response != null) {
                    navigateToLogin()
                    Log.d("response", response.toString())
                }
            }
        }

    }
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)

        startActivity(intent)
        finish()
    }
}