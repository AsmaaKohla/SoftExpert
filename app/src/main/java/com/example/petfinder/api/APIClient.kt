package com.example.petfinder.api

import com.example.petfinder.data.response.AuthResponse
import com.example.petfinder.data.request.AuthBody
import com.example.petfinder.data.response.PetDataListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIClient {

    @POST("oauth2/token")
    fun refreshToken(
        @Body type: AuthBody,
    ): Single<Response<AuthResponse>>

    @GET("animals")
    fun getPetList(
        @Query("type") type: String,
        @Query("page") page: Int?
    ): Single<Response<PetDataListResponse>>
}