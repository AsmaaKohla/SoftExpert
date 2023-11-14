package com.example.petfinder.data.appData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PetData(
    val id: Int,
    val url: String,
    val type: String,
    val gender: String,
    val size: String,
    val name: String,
    val color: String,
    val address: String,
    val smallImage: String?,
    val largeImage: String?
) : Parcelable