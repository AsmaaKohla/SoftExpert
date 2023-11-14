package com.example.petfinder.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    protected val showProgressBarMutableLiveData = MutableLiveData<Boolean>()
    val showProgressBarLiveData: LiveData<Boolean> get() = showProgressBarMutableLiveData

    val toastLiveData: LiveData<String> get() = toastMutableLiveData
    protected val toastMutableLiveData = MutableLiveData<String>()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun clearDisposables() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        clearDisposables()
    }
}