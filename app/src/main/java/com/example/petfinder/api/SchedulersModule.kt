package com.example.petfinder.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SchedulersModule {
    companion object {
        const val OBSERVED = "observed"
        const val OBSERVER = "observer"
    }

    @Singleton
    @Provides
    @Named(OBSERVER)
    fun provideObserverScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Singleton
    @Provides
    @Named(OBSERVED)
    fun provideObservedOnScheduler(): Scheduler {
        return Schedulers.io()
    }
}