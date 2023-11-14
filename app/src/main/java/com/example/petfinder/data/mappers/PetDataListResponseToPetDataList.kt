package com.example.petfinder.data.mappers

import com.example.petfinder.data.appData.Pagination
import com.example.petfinder.data.appData.PetData
import com.example.petfinder.data.appData.PetDataList
import com.example.petfinder.data.response.AddressResponse
import com.example.petfinder.data.response.AnimalPhotoResponse
import com.example.petfinder.data.response.PetDataListResponse
import javax.inject.Inject

class PetDataListResponseToPetDataList @Inject constructor() :
    Mapper<PetDataListResponse, PetDataList>() {

    override fun mapFrom(from: PetDataListResponse): PetDataList {

        val animals = from.animals.map {
            val smallestImage = it.photos?.let { list -> getSmallestImage(list) }
            val largestImage = it.photos?.let { list -> getLargestImage(list) }
            val address = it.contact?.let { contact -> formatAddress(contact.address) }
            PetData(
                it.id,
                it.url ?: "NA",
                it.type ?: "NA",
                it.gender ?: "NA",
                it.size ?: "NA",
                it.name ?: "NA",
                it.colors?.primary ?: "NA",
                address ?: "NA",
                smallestImage,
                largestImage
            )
        }

        val pagination = from.pagination.let {
            Pagination(it.count_per_page, it.total_count, it.current_page, it.total_pages)
        }

        return PetDataList(animals, pagination)
    }

    private fun getSmallestImage(photos: List<AnimalPhotoResponse>): String? {
        photos.forEach {
            val image = it.small ?: it.medium ?: it.large ?: it.full
            image.let { return image }
        }

        return null
    }

    private fun getLargestImage(photos: List<AnimalPhotoResponse>): String? {
        photos.forEach {
            val image = it.full ?: it.large ?: it.medium ?: it.small
            image.let { return image }
        }

        return null
    }

    private fun formatAddress(addressResponse: AddressResponse?): String? {
        if (addressResponse == null || (addressResponse.city == null && addressResponse.state == null && addressResponse.country == null)) return null

        var address = ""
        var separator = ""
        addressResponse.city?.let { city ->
            address = city
            separator = " ,"
        }
        addressResponse.state?.let { state ->
            address = "$separator$state"
            separator = " ,"
        }
        addressResponse.country?.let { country ->
            address = "$separator$country"
        }
        return address
    }
}