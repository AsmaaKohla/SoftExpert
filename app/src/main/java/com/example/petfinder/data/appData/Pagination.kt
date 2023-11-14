package com.example.petfinder.data.appData

data class Pagination(
    val countPerPage: Int,
    val totalCount: Int,
    val currentPage: Int,
    val totalPages: Int
)
