package com.example.petfinder.data.response

data class PaginationResponse(
    val count_per_page: Int, val total_count: Int, val current_page: Int, val total_pages: Int
)
