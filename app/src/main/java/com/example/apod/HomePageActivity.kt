package com.example.apod

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class HomePageActivity : AppCompatActivity() {
    var picker: DatePickerDialog? = null
    var eText: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        eText = findViewById<View>(R.id.editText1) as EditText
        eText!!.inputType = InputType.TYPE_NULL
        eText!!.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]
            // date picker dialog
            picker = DatePickerDialog(this@HomePageActivity,
                    { view, year, monthOfYear, dayOfMonth -> eText!!.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year) }, year, month, day)
            picker!!.show()
        }
    }
}