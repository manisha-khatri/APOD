package com.example.apod.views.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apod.R
import com.example.apod.model.APODImageData
import com.example.apod.viewModel.APODDataVM
import com.example.apod.views.adapter.SavedImagesAdapter
import com.example.apod.views.state.BookmarkImagesState
import kotlinx.android.synthetic.main.bookmark_image_activity.*

class BookmarkedImageActivity: AppCompatActivity() {

    var apodDataVM: APODDataVM ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bookmark_image_activity)

        initViews()
        apodDataVM?.fetchImages(application)
        listener()
    }

    fun setNewsInRecyclerViewAdapter(recyclerView: RecyclerView, bookmarkedNewsList: List<APODImageData?>?) {
        val adapter = SavedImagesAdapter(bookmarkedNewsList, this)
        recyclerView.setAdapter(adapter)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        recyclerView.setLayoutManager(linearLayoutManager)
    }

    private fun listener() {
        apodDataVM?.bookmarkedImagesState?.observe(this) {
            try {
                when (it) {
                    is BookmarkImagesState.Success -> {
                        loaderContainer.visibility = View.GONE
                        errorContainer.visibility = View.GONE
                        recyclerviewSavedImages.visibility = View.VISIBLE

                        setNewsInRecyclerViewAdapter(recyclerviewSavedImages, it.list)
                    }
                    is BookmarkImagesState.Error -> {
                        loaderContainer.visibility = View.GONE
                        errorContainer.visibility = View.VISIBLE
                        recyclerviewSavedImages.visibility = View.GONE
                    }
                    is BookmarkImagesState.Loading -> {
                        loaderContainer.visibility = View.VISIBLE
                        errorContainer.visibility = View.GONE
                        recyclerviewSavedImages.visibility = View.GONE
                    }
                }
            }catch (e: Exception){
            }
        }
    }

    fun initViews(){
        apodDataVM = ViewModelProviders.of(this).get(APODDataVM::class.java)
    }
}