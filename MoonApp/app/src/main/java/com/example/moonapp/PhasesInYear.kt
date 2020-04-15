package com.example.moonapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_phases_in_year.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.util.*

class PhasesInYear : AppCompatActivity() {

    val REQUEST_CODE = 20000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phases_in_year)

//        val extras = intent.extras ?: return
//        val message = extras.getString("Parametr") // odebranie info
//        statusText.text = message
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        var year = dateInput.text.toString().toInt()
        moonInMonths(year)
    }


    fun algorithmSettingsClick(v: View){
        val i = Intent(this, AlgorithmSettings::class.java)
        i.putExtra("Parametr", "Twoje Dane") // Wysłanie danych
        startActivityForResult(i,REQUEST_CODE) // wysłanie kodu
    }

    // Jesli ktos wykonal akcje, to oblicz na nowo pola
    @RequiresApi(Build.VERSION_CODES.O)
    fun addYearClick(v: View){
        var year = dateInput.text.toString().toInt() + 1

        if(year > 2200)
            year = 2200

        dateInput.setText(year.toString())
        moonInMonths(year)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun substractYearClick(v: View){
        var year = dateInput.text.toString().toInt() - 1

        if(year < 1900)
            year = 1900

        dateInput.setText(year.toString())
        moonInMonths(year)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun dateInputClick(v: View){
        var year = dateInput.text.toString().toInt()
        moonInMonths(year)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun moonInMonths(year: Int){
        var moonDay = 0
        val data = readFile()
        val dataList = data.split(";")

        when (dataList[1]){
            "Simple" -> {
                moonDay = Algorithm.simple(year, 1, 31)
            }
            "Conway"->{
                moonDay = Algorithm.conway(year, 1, 31)
            }
            "Trig1"->{
                moonDay = Algorithm.trig1(year, 1, 31)
            }
            "Trig2"->{
                moonDay = Algorithm.trig2(year, 1, 31)
            }
        }

        val dayMoon = fullnessInMonth(31 - moonDay)
        val dateMoon = LocalDate.of(year,1, dayMoon)

        dateMoon1.text = dateMoon.dayOfMonth.toString() + ".01." + year.toString()

        dateMoon2.text =  dateMoon.plusDays(30).dayOfMonth.toString() + ".02." + year.toString()
        dateMoon3.text = dateMoon.plusDays(59).dayOfMonth.toString() + ".03." + year.toString()
        dateMoon4.text = dateMoon.plusDays(89).dayOfMonth.toString() + ".04." + year.toString()
        dateMoon5.text = dateMoon.plusDays(118).dayOfMonth.toString() + ".05." + year.toString()
        dateMoon6.text = dateMoon.plusDays(148).dayOfMonth.toString() + ".06." + year.toString()
        dateMoon7.text = dateMoon.plusDays(177).dayOfMonth.toString() + ".07." + year.toString()
        dateMoon8.text = dateMoon.plusDays(207).dayOfMonth.toString() + ".08." + year.toString()
        dateMoon9.text = dateMoon.plusDays(236).dayOfMonth.toString() + ".09." + year.toString()
        dateMoon10.text = dateMoon.plusDays(266).dayOfMonth.toString() + ".10." + year.toString()
        dateMoon11.text = dateMoon.plusDays(295).dayOfMonth.toString() + ".11." + year.toString()
        dateMoon12.text = dateMoon.plusDays(325).dayOfMonth.toString() + ".12." + year.toString()
    }

    private fun fullnessInMonth(day: Int): Int{
        if(day - 14 > 0)
            return (day - 14)
        else
            return (day + 15)
    }

    fun readFile(): String{
        val defaultValue = "S;Simple"
        try{
            val fileName = "algorithm.txt"
            if(FileExist(fileName)){
                val file = InputStreamReader(openFileInput((fileName)))
                val br = BufferedReader(file)

                var line = br.readLine()
                file.close()
                return line
            }
            else{
                val file = OutputStreamWriter(openFileOutput(fileName, Context.MODE_PRIVATE))
                file.write(defaultValue)
                file.flush()
                file.close()
            }

        }catch (e: Exception){

        }
        return defaultValue
    }

    fun FileExist(path: String): Boolean{
        val file = baseContext.getFileStreamPath(path)
        return file.exists()
    }
}
