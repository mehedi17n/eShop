package com.example.eshop.data.login

data class LoginResponse(
    val message: String?,
    val token: String?,
    val user: User?
)