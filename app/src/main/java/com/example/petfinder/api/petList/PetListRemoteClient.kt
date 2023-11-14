package com.example.petfinder.api.petList

import com.example.petfinder.api.APIClient
import com.example.petfinder.api.NetworkConnectivity
import com.example.petfinder.api.authentication.AuthRemoteClient
import com.example.petfinder.data.appData.PetDataList
import com.example.petfinder.data.mappers.PetDataListResponseToPetDataList
import com.example.petfinder.data.response.Resource
import io.reactivex.rxjava3.core.Single
import okhttp3.internal.wait
import javax.inject.Inject

class PetListRemoteClient @Inject constructor(
    private val apiClient: APIClient,
    private val mapper: PetDataListResponseToPetDataList,
    private val networkConnectivity: NetworkConnectivity,
    private val authRemoteClient: AuthRemoteClient
) {

    fun getPetList(
        type: String,
        page: Int?,
        retryFlag: Boolean = true
    ): Single<Resource<PetDataList>> {
        return if (networkConnectivity.isConnected()) {
            apiClient.getPetList(type, page).map { response ->
                if (response.isSuccessful) {
                    if (retryFlag && response.code() == 403) {
                        handleExpiredToken(type, page)
                    } else {
                        response.body()?.let {
                            val petDataList = mapper.mapFrom(it)
                            Resource.Success(petDataList)
                        } ?: kotlin.run { Resource.DataError(Error(response.message())) }
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

    private fun handleExpiredToken(
        type: String,
        page: Int?
    ): Resource<PetDataList> {
        authRemoteClient.refreshToken().blockingGet()
        return getPetList(type, page, false).blockingGet()
    }
}