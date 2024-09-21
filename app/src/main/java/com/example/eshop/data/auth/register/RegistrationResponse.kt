package com.example.eshop.data.auth.register

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegistrationResponse(
    val message: String?,
    val status: Int?
): Parcelable