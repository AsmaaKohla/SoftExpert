package com.example.petfinder.api

import com.example.petfinder.api.authentication.AuthRepo
import com.example.petfinder.api.authentication.AuthRepoImpl
import com.example.petfinder.api.petList.PetListRepo
import com.example.petfinder.api.petList.PetListRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ReposModule {

    @Singleton
    @Provides
    fun provideRepo(repoImpl: AuthRepoImpl): AuthRepo = repoImpl

    @Singleton
    @Provides
    fun providePetRepo(repoImpl: PetListRepoImpl): PetListRepo = repoImpl
}