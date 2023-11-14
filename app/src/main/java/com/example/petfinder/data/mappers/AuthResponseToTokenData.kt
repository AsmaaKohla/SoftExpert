package com.example.petfinder.data.mappers

import com.example.petfinder.data.appData.TokenData
import com.example.petfinder.data.response.AuthResponse
import javax.inject.Inject

class AuthResponseToTokenData @Inject constructor() : Mapper<AuthResponse, TokenData>() {

    override fun mapFrom(from: AuthResponse): TokenData {
        return TokenData("${from.tokenType} ${from.accessToken}")
    }
}