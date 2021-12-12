package com.example.apod.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apod.model.APODImageData
import com.example.apod.repository.APIRepository
import com.example.apod.views.state.HomePageState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class APODDataVM: ViewModel() {

    var viewState: MutableLiveData<HomePageState> = MutableLiveData<HomePageState>()
    var repository: APIRepository
    private val compositeDisposable = CompositeDisposable()

    init {
        repository = APIRepository.getInstance()
    }

    fun fetchAPODData(date: String) {
        viewState.value = HomePageState.Loading

        compositeDisposable.add(
            repository.fetchAPODData(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFetchAPODDataSuccess, this::onFetchAPODDataError)
        )
    }

    fun onFetchAPODDataSuccess(apodImageData: APODImageData) {
        TODO("Not yet implemented")
    }

    fun onFetchAPODDataError(throwable: Throwable) {
        TODO("Not yet implemented")
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}


