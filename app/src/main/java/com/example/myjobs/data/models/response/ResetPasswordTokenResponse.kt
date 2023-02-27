package com.example.myjobs.data.models.response

import androidx.annotation.Keep
import java.io.Serializable


@Keep
data class ResetPasswordTokenResponse (
    val email: String,
    val token: String
): Serializable