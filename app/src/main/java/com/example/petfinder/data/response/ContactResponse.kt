package com.example.petfinder.data.response

data class ContactResponse(
    val email: String?,
    val phone: String?,
    val address: AddressResponse?
)