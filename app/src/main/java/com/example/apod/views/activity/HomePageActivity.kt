package com.example.apod.views.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.apod.R
import com.example.apod.model.APODImageData
import com.example.apod.viewModel.APODDataVM
import com.example.apod.views.state.BookmarkState
import com.example.apod.views.state.HomePageState
import kotlinx.android.synthetic.main.activity_home_page.*
import java.util.*


class HomePageActivity : AppCompatActivity() {

    var datePickerDialog: DatePickerDialog? = null
    var apodDataVM: APODDataVM ?= null
    var imagesSavedInDB = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        setToolbar()
        initViews()
        registerListeners()
    }

    private fun setToolbar() {
        setSupportActionBar(imageToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    fun initViews(){
        initPageContainer.visibility = View.VISIBLE
        datePickerET?.inputType = InputType.TYPE_NULL
        apodDataVM = ViewModelProviders.of(this).get(APODDataVM::class.java)
    }

    fun dateViewClickListener(){
        val cldr = Calendar.getInstance()
        val day = cldr[Calendar.DAY_OF_MONTH]
        val month = cldr[Calendar.MONTH]
        val year = cldr[Calendar.YEAR]
        // date picker dialog, date: YYYY-MM-DD
        datePickerDialog = DatePickerDialog(
            this@HomePageActivity,
            { view, year, monthOfYear, dayOfMonth ->
                val date: String =
                    year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString()
                datePickerET?.setText(date)
                initPageContainer.visibility = View.GONE
                apodDataVM?.fetchAPODData(date)
            }, year, month, day
        )
        datePickerDialog?.getDatePicker()?.setMaxDate(System.currentTimeMillis());
        datePickerDialog?.show()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun registerListeners() {

        bookmarkedImages.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, BookmarkedImageActivity::class.java)
            startActivity(intent)
        })

        datePickerET?.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                dateViewClickListener()
                return@OnTouchListener true
            }
            false
        })

        apodDataVM?.bookmarkState?.observe(this) {
            try {
                when (it) {
                    is BookmarkState.Saved -> {
                        checkBookmark()
                    }
                    is BookmarkState.Unsaved -> {
                        uncheckBookmark()
                    }
                }
            }catch (e: Exception){
            }
        }

        apodDataVM?.viewState?.observe(this) {
            try {
                when (it) {
                    is HomePageState.Success -> {
                        val apodImageData = it.data
                        if (apodImageData.url != null) {
                            Glide.with(this)
                                .load(apodImageData.url)
                                .into(imageIV)
                        } else if (apodImageData.hdurl != null) {
                            Glide.with(this)
                                .load(apodImageData.hdurl)
                                .into(imageIV)
                        } else
                            imageIV.visibility = View.GONE

                        imageTitle1.text = apodImageData.title
                        dateTV.text = apodImageData.date
                        imageDescription.text = apodImageData.explanation

                        bookmarkImage.setOnClickListener(View.OnClickListener {
                            if (imagesSavedInDB) {
                                uncheckBookmark()
                                apodImageData.date?.let {
                                    apodDataVM?.deleteImage(
                                        it,
                                        application
                                    );
                                }
                            } else {
                                checkBookmark()
                                apodDataVM?.saveImage(apodImageData, application)
                            }
                        })

                        apodDataContainer.visibility = View.VISIBLE
                        errorContainer.visibility = View.GONE
                        progressBarContainer.visibility = View.GONE
                        apodImageData?.let {
                            apodDataVM?.searchImage(
                                apodImageData?.date,
                                application
                            )
                        }
                    }
                    is HomePageState.Error -> {
                        errorTV.text = it.message
                        errorContainer.visibility = View.VISIBLE
                        progressBarContainer.visibility = View.GONE
                        apodDataContainer.visibility = View.GONE
                    }
                    is HomePageState.Loading -> {
                        progressBarContainer.visibility = View.VISIBLE
                        errorContainer.visibility = View.GONE
                        apodDataContainer.visibility = View.GONE
                    }
                }
            }catch (e: Exception){
            }
        }
    }


    fun uncheckBookmark() {
        bookmarkImage.setImageResource(R.drawable.final_bookmark)
        imagesSavedInDB = false
    }

    fun checkBookmark() {
        bookmarkImage.setImageResource(R.drawable.ic_baseline_bookmark_24)
        imagesSavedInDB = true
    }

}