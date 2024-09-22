package com.example.eshop.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var userName: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var registerbtn: Button
    private lateinit var viewModel: MainViewModel
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

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        //onclick Listeners
        optionLogin.setOnClickListener {
            navigateToLogin()
        }

        registerbtn.setOnClickListener {
            val name = userName.text.toString()
            val phoneNo = phoneNumber.text.toString()
            val email = userEmail.text.toString()
            val password = userPassword.text.toString()

            // Call the validation function
            val valid = validateForm(name, phoneNo, email, password)
            println("valid $valid")
            if (valid) {
                viewModel.onCreateAccount(name, phoneNo, email, password)

                lifecycleScope.launch {
                    viewModel.registrationResponse.collect { response ->
                        println("response ${response}");
                        if (response != null) {
                            if (response.status == 200) {
                                Toast.makeText(this@RegisterActivity, response.message, Toast.LENGTH_SHORT).show()
                                Log.d("Registration", response.toString())
                                navigateToLogin(response)
                                finish()
                            } else {
                                Log.d("Registration 1", response.status.toString());

                                Toast.makeText(this@RegisterActivity, response.message, Toast.LENGTH_SHORT).show()
                            }

                        }

                    }
                }
            }

        }
    }

    private fun navigateToLogin(response: RegistrationResponse) {
        val intent = Intent(this, LoginActivity::class.java).apply {
            putExtra("REGISTER", response)
        }

        startActivity(intent)
        finish()
    }

    private fun validateForm(
        username: String,
        phone: String,
        email: String,
        password: String
    ): Boolean {
        var isValid = true

        // Validate username
        if (username.isEmpty()) {
            userName.error = "Please enter a username"
            isValid = false
        } else {
            userName.error = null
        }

        // Validate phone number
        if (phone.isEmpty() || phone.length != 10 || phone[0] != '1') {
            phoneNumber.error = "Please enter a valid phone number"
            isValid = false
        } else {
            phoneNumber.error = null
        }

        // Validate email
        if (email.isEmpty() || !isValidEmail(email)) {
            userEmail.error = "Please enter a valid email address"
            isValid = false
        } else {
            userEmail.error = null
        }

        // Validate password
        if (password.isEmpty() || password.length < 6) {
            userPassword.error = "Password must be at least 6 characters"
            isValid = false
        } else {
            userPassword.error = null
        }

        // If all fields are valid, show success message
        if (isValid) {
            // Clear the form fields
            userName.text?.clear()
            phoneNumber.text?.clear()
            userEmail.text?.clear()
            userPassword.text?.clear()
            return true

        } else {
            Toast.makeText(this, "Please fill in all fields correctly!", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}


