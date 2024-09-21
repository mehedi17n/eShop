package com.example.eshop.data.auth.login

import com.example.eshop.data.authentication.login.User

data class LoginResponse(
    val message: String?,
    val token: String?,
    val user: User?
)