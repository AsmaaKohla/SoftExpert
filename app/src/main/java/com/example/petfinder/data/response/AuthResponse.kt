package com.example.petfinder.data.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: String,
    @SerializedName("access_token") val accessToken: String
)