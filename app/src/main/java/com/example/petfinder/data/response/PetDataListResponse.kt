package com.example.petfinder.data.response

data class PetDataListResponse(
    val animals: List<PetDataResponse>,
    val pagination: PaginationResponse
)