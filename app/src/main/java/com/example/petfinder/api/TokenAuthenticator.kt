package com.example.petfinder.api

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor() : Authenticator {

    companion object {
        var accessToken: String? = null
    }

    fun saveAccessToken(token: String) {
        accessToken = token
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        return response.request.newBuilder()
            .addHeader(
                "Authorization",
                accessToken ?: ""
            ).build()
    }
}