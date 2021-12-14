package com.example.apod.views.state

import com.example.apod.model.APODImageData

sealed class BookmarkImagesState {
    data class Success(val list: List<APODImageData>) : BookmarkImagesState()
    object Loading : BookmarkImagesState()
    data class Error(val message: String) : BookmarkImagesState()
}
