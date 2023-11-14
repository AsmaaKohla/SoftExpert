package com.example.petfinder.data.response

sealed class Resource<T>() {
    class Success<T>(val data: T) : Resource<T>()
    class DataError<T>(val error: Error) : Resource<T>()
}