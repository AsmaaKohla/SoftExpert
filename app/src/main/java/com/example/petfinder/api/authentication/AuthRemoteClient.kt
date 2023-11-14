package com.example.petfinder.api.authentication

import com.example.petfinder.api.APIClient
import com.example.petfinder.api.NetworkConnectivity
import com.example.petfinder.api.TokenAuthenticator
import com.example.petfinder.data.mappers.AuthResponseToTokenData
import com.example.petfinder.data.request.AuthBody
import com.example.petfinder.data.response.Resource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AuthRemoteClient @Inject constructor(
    private val apiClient: APIClient,
    private val tokenAuthenticator: TokenAuthenticator,
    private val mapper: AuthResponseToTokenData,
    private val networkConnectivity: NetworkConnectivity
) {
    fun refreshToken(): Single<Resource<Boolean>> {

        // this values has to be returned from the login and saved safely
        val apiKey = "DZo7FfdrS2BMauQ38W9I8WS5xthNDZNClC56jAPdisMkkfq3jk"
        val secret = "3bztqgJBUrgHRaIIhl5U1zyeoZbyoehg3JEQxCtR"

        return if (networkConnectivity.isConnected()) {
            apiClient.refreshToken(AuthBody(apiKey, secret)).map { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        val token = mapper.mapFrom(it)
                        tokenAuthenticator.saveAccessToken(token.token)
                        Resource.Success(true)
                    } ?: kotlin.run {
                        Resource.DataError(Error(response.message()))
                    }
                } else {
                    Resource.DataError(Error("Request not successful"))
                }
            }
        } else {
            return Single.create { emitter ->
                emitter.onError(Throwable(Error("Network Error")))
            }
        }
    }
}