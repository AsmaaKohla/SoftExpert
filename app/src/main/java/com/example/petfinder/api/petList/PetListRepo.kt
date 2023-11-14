package com.example.petfinder.api.petList

import com.example.petfinder.data.appData.PetDataList
import com.example.petfinder.data.response.Resource
import io.reactivex.rxjava3.core.Single

interface PetListRepo {
    fun getPetList(type: String, page: Int?): Single<Resource<PetDataList>>
}