package com.example.petfinder.api.authentication

import com.example.petfinder.data.appData.TokenData
import com.example.petfinder.data.response.Resource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val remoteClient: AuthRemoteClient,
) : AuthRepo {
    override fun refreshToken(): Single<Resource<Boolean>> {
        return remoteClient.refreshToken()
    }
}