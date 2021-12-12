package com.example.apod.views.state

import com.example.apod.model.APODImageData

sealed class HomePageState {
    data class Success(val data: APODImageData) : HomePageState()
    data class Error(val message: String) : HomePageState()
    object Loading : HomePageState()
}
