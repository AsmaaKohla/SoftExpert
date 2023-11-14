package com.example.petfinder.api.petList

import com.example.petfinder.data.appData.PetDataList
import com.example.petfinder.data.response.Resource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PetListRepoImpl @Inject constructor(
    private val remoteClient: PetListRemoteClient,
) : PetListRepo {
    override fun getPetList(type: String, page: Int?): Single<Resource<PetDataList>> {
        return remoteClient.getPetList(type, page)
    }
}