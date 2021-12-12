package com.example.apod.views.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProviders
import com.example.apod.R
import com.example.apod.viewModel.APODDataVM
import com.example.apod.views.state.HomePageState
import java.util.*

class HomePageActivity : AppCompatActivity() {

    var datePickerDialog: DatePickerDialog? = null
    var datePickerET: EditText ?= null
    var apodDataVM: APODDataVM ?= null
    var datePickerCard: CardView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        initViews()
        registerListeners()
    }

    fun initViews(){
        datePickerET = findViewById<View>(R.id.datePickerET) as EditText
        datePickerCard = findViewById(R.id.datePickerCard)
        datePickerET?.inputType = InputType.TYPE_NULL
        apodDataVM = ViewModelProviders.of(this).get(APODDataVM::class.java)
    }

    private fun registerListeners() {
        datePickerET?.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]
            // date picker dialog, date: YYYY-MM-DD
            datePickerDialog = DatePickerDialog(this@HomePageActivity,
                { view, year, monthOfYear, dayOfMonth ->
                    val date: String = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString()
                    datePickerET?.setText(date)
                    apodDataVM?.fetchAPODData(date)

                }, year, month, day
            )
            datePickerDialog?.getDatePicker()?.setMaxDate(System.currentTimeMillis());
            datePickerDialog?.show()
        }

        apodDataVM?.viewState?.observe(this) {
            when (it) {
                is HomePageState.Success -> {
                    // tvUserName.text = it.data.name
                }
                is HomePageState.Error -> {
                    //   tvError.text = it.message
                }
                is HomePageState.Loading -> {
                }
            }
        }

    }


}