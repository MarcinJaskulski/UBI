package com.example.moonapp

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_algorithm_settings.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.time.LocalDateTime
import java.util.*
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity() {

    val REQUEST_CODE = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        val currentDate = LocalDateTime.now()
        var moonDay = 0

        val data = readFile()
        val dataList = data.split(";")

        when (dataList[1]){
            "Simple" -> {
                moonDay = Algorithm.simple(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth)
            }
            "Conway"->{
                moonDay = Algorithm.conway(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth)
            }
            "Trig1"->{
                moonDay = Algorithm.trig1(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth)
            }
            "Trig2"->{
                moonDay = Algorithm.trig2(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth)
            }
        }

        var phasePercent = (moonDay.toDouble()*100 / 30).roundToInt()
        todayPhase.text = "Dzisiaj: " + phasePercent  + "%"

        val lastMoonDate = currentDate.minusDays(moonDay.toLong())
        lastMoon.text = "Poprzedni nów: "  + lastMoonDate.dayOfMonth+"." + lastMoonDate.monthValue + "." + lastMoonDate.year

        var itWas = ""
        // it was
        if(moonDay - 14 > 0){
            val nextMoonDate = currentDate.plusDays(30 - moonDay.toLong() - 15 + 30)
            nextMoon.text = "Następna pełnia: " + nextMoonDate.dayOfMonth + "." + nextMoonDate.monthValue + "." + nextMoonDate.year
            itWas = "_"
            phasePercent = (100 - phasePercent)*2
        }
        // not yet
        else{
            val nextMoonDate = currentDate.plusDays(30 - moonDay.toLong() + 15)
            nextMoon.text = "Następna pełnia: " +  nextMoonDate.dayOfMonth+ "." + nextMoonDate.monthValue + "." + nextMoonDate.year
            phasePercent *=2
        }

        // Photo
        val photoName =  dataList[0].toLowerCase() + choosMoonPhoto(phasePercent).toString() + "p" + itWas
        val path = resources.getIdentifier(photoName, "drawable", packageName)
        if(path == 0)
            Toast.makeText(this, "Cos nie tak", Toast.LENGTH_LONG).show()
        val photo = findViewById<ImageView>(R.id.imageMoonPhase)
        photo.setImageResource(path)
    }

    fun moonPhasesInYearClick(v: View){
        val i = Intent(this, PhasesInYear::class.java)
        i.putExtra("Parametr", "Twoje Dane") // Wysłanie danych
        startActivityForResult(i,REQUEST_CODE) // wysłanie kodu
    }

    private fun choosMoonPhoto(percent: Int) :Int{
        when(percent){
            in 0..2 ->{
                return 0
            }
            in 3..5 ->{
                return 3
            }
            in 6..10 ->{
                return 6
            }
            in 11..17 ->{
                return 11
            }
            in 18..27 ->{
                return 18
            }
            in 28..36 ->{
                return 28
            }
            in 37..47 ->{
                return 37
            }
            in 48..59 ->{
                return 48
            }
            in 60..69 ->{
                return 60
            }
            in 70..79 ->{
                return 70
            }
            in 80..84 ->{
                return 80
            }
            in 85..89 ->{
                return 85
            }
            in 90..94 ->{
                return 90
            }
            in 95..98 ->{
                return 95
            }
            in 99..100 -> {
                return 99
            }
            else ->{
                Toast.makeText(this, "Nie ma takiego procenta", Toast.LENGTH_LONG).show()
                return 0
            }
        }
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
