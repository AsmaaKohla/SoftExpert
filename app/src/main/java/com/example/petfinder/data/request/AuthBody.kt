package com.example.petfinder.data.request

import com.google.gson.annotations.SerializedName

data class AuthBody(
    @SerializedName("client_id") val client_id: String,
    @SerializedName("client_secret") val client_secret: String,
    @SerializedName("grant_type") val grant_type: String = "client_credentials"
)