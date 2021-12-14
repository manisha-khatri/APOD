package com.example.apod.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apod.database.DBImagesRepository
import com.example.apod.database.DBRepositoryCallBack
import com.example.apod.database.DBRepositorySearchImagesCallBck
import com.example.apod.model.APODImageData
import com.example.apod.repository.APIRepository
import com.example.apod.views.state.BookmarkImagesState
import com.example.apod.views.state.BookmarkState
import com.example.apod.views.state.HomePageState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class APODDataVM: ViewModel() {

    var viewState: MutableLiveData<HomePageState> = MutableLiveData<HomePageState>()
    var bookmarkState: MutableLiveData<BookmarkState> = MutableLiveData<BookmarkState>()
    var bookmarkedImagesState: MutableLiveData<BookmarkImagesState> = MutableLiveData<BookmarkImagesState>()
    var apiRepository: APIRepository
    var dbRepository: DBImagesRepository ?= null
    private val compositeDisposable = CompositeDisposable()

    init {
        apiRepository = APIRepository.getInstance()
    }

    fun fetchAPODData(date: String) {
        viewState.value = HomePageState.Loading

        compositeDisposable.add(
            apiRepository.fetchAPODData(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFetchAPODDataSuccess, this::onFetchAPODDataError)
        )
    }

    fun deleteImage(date: String, application: Application){
        dbRepository = DBImagesRepository(application)
        dbRepository?.deleteImagesByPublishDate(date)
    }

    fun saveImage(apodImageData: APODImageData, application: Application){
        dbRepository = DBImagesRepository(application)
        dbRepository?.saveImagesInDB(apodImageData)
    }

    fun searchImage(publishDate: String?, application: Application?) {
        dbRepository = DBImagesRepository(application)
        dbRepository?.searchImagesByPublishDate(object : DBRepositorySearchImagesCallBck {
            override fun isImagesFound(result: Boolean) {
                if (result == true)
                    bookmarkState.value = BookmarkState.Saved
                else
                    bookmarkState.value = BookmarkState.Unsaved
            }
        }, publishDate)
    }

    fun fetchImages(application: Application?) {
        bookmarkedImagesState.value = BookmarkImagesState.Loading
        dbRepository = DBImagesRepository(application)
        dbRepository?.fetchImagesFromDB(object : DBRepositoryCallBack {
            override fun onFailureResponse(msg: String?) {
                bookmarkedImagesState.value = BookmarkImagesState.Error(msg.toString())
            }
            override fun onSuccessfulResponse(bookmarkedImages: List<APODImageData>?) {
                if(bookmarkedImages != null) bookmarkedImagesState.value = BookmarkImagesState.Success(bookmarkedImages)
                else  bookmarkedImagesState.value = BookmarkImagesState.Error("Some error ocurred")
            }
        })
    }


    fun onFetchAPODDataSuccess(apodImageData: APODImageData) {
        viewState.value = HomePageState.Success(apodImageData)
    }

    fun onFetchAPODDataError(throwable: Throwable) {
        viewState.value = HomePageState.Error(throwable.message.toString())
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}


