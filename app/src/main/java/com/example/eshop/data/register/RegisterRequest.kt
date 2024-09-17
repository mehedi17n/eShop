package com.example.eshop.data.register

data class RegisterRequest(
    val name: String,
    val phone: String,
    val email: String,
    val password: String
)
