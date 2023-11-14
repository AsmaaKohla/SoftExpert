package com.example.petfinder.data.mappers

abstract class Mapper<T, E> {
    abstract fun mapFrom(from: T): E
}