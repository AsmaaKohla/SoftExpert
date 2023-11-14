package com.example.petfinder.presentation.petsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.petfinder.api.authentication.AuthRepo
import com.example.petfinder.api.petList.PetListRepo
import com.example.petfinder.presentation.base.BaseViewModel
import com.example.petfinder.data.response.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.SingleObserver
import javax.inject.Inject
import javax.inject.Named
import com.example.petfinder.api.SchedulersModule.Companion.OBSERVER
import com.example.petfinder.api.SchedulersModule.Companion.OBSERVED
import com.example.petfinder.data.appData.PetDataList
import io.reactivex.rxjava3.disposables.Disposable

@HiltViewModel
class PetsViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val petListRepo: PetListRepo,
    @Named(OBSERVER) private val observer: Scheduler,
    @Named(OBSERVED) private val observed: Scheduler
) : BaseViewModel() {

    private val userAuthorizedMutableLiveData = MutableLiveData<Boolean>()
    val userAuthorizedLiveData: LiveData<Boolean> get() = userAuthorizedMutableLiveData

    private val petListMutableLiveData = MutableLiveData<PetDataList>()
    val petListLiveData: LiveData<PetDataList> get() = petListMutableLiveData

    private var selectedType: String = ""
    private var isUserAuthonticated = false

    fun setSelectedType(type: String) {
        selectedType = if (type == "All") "" else type
    }

    fun authUser() {
        if (isUserAuthonticated) return
        authRepo.refreshToken().observeOn(observer).subscribeOn(observed)
            .subscribe(object :
                SingleObserver<Resource<Boolean>> {
                override fun onSubscribe(d: Disposable) {
                    addDisposable(d)
                    showProgressBarMutableLiveData.value = true
                }

                override fun onSuccess(state: Resource<Boolean>) {
                    showProgressBarMutableLiveData.value = false
                    when (state) {
                        is Resource.Success -> {
                            userAuthorizedMutableLiveData.postValue(state.data!!)
                            isUserAuthonticated = true
                        }

                        is Resource.DataError -> {
                            toastMutableLiveData.value = state.error.message
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    showProgressBarMutableLiveData.value = false
                    toastMutableLiveData.value = e.message
                }
            })
    }

    fun loadPets(page: Int? = null) {
        petListRepo.getPetList(selectedType, page).observeOn(observer).subscribeOn(observed)
            .subscribe(object :
                SingleObserver<Resource<PetDataList>> {
                override fun onSubscribe(d: Disposable) {
                    addDisposable(d)
                    showProgressBarMutableLiveData.value = true
                }

                override fun onSuccess(state: Resource<PetDataList>) {
                    showProgressBarMutableLiveData.value = false
                    when (state) {
                        is Resource.Success -> {
                            val data = state.data
                            petListMutableLiveData.value = data
                        }

                        is Resource.DataError -> {
                            toastMutableLiveData.value = state.error.message
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    showProgressBarMutableLiveData.value = false
                    toastMutableLiveData.value = e.message
                }
            })
    }
}