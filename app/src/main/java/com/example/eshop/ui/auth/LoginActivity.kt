package com.example.eshop.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.eshop.R
import com.example.eshop.datasource.DataStoreKeys
import com.example.eshop.ui.home.MainActivity
import com.example.eshop.viewModel.LoginViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var loginbtn: Button
    private lateinit var optionRegister: TextView
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        // References
        userEmail = findViewById(R.id.etEmailLogin)
        userPassword = findViewById(R.id.etPasswordLogin)
        loginbtn = findViewById(R.id.loginButton)
        optionRegister = findViewById(R.id.tvSignup)

        // Set up text watchers for email and password input
        setupTextWatchers()

        // Observe StateFlows for changes
        observeViewModel()

        // OnClick Listeners
        optionRegister.setOnClickListener {
            navigateToRegistration()
        }

        loginbtn.setOnClickListener {
            viewModel.onCLickLogin(this)
        }
    }

    override fun onStart() {
        super.onStart()
        // Set initial text for the email and password fields
        userEmail.setText(viewModel.emailStateFlow.value)
        userPassword.setText(viewModel.passwordStateFlow.value)
    }

    private fun setupTextWatchers() {
        // Email text watcher
        userEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEmailChanged(s.toString())
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
        lifecycleScope.launch {
            viewModel.loginResponse.collect { response ->
                if(response != null)
                    navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToRegistration() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
