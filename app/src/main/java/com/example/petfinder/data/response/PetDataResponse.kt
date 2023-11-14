package com.example.petfinder.data.response

import com.google.gson.annotations.SerializedName

data class PetDataResponse(
    val id: Int,
    @SerializedName("organization_id") val organizationId: String,
    val url: String?,
    val type: String?,
    val species: String?,
    val age: String?,
    val gender: String?,
    val size: String?,
    val name: String?,
    val photos: List<AnimalPhotoResponse>?,
    val contact: ContactResponse?,
    val colors: ColorsResponse?
)