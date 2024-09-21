package com.example.eshop.data.auth.register

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateRegistration(
    val name: String?,
    val phone: String?,
    val email: String?,
    val password: String?
): Parcelable
