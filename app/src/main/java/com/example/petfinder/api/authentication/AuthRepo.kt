package com.example.petfinder.api.authentication

import com.example.petfinder.data.appData.TokenData
import com.example.petfinder.data.response.Resource
import io.reactivex.rxjava3.core.Single

interface AuthRepo {
    fun refreshToken(): Single<Resource<Boolean>>
}