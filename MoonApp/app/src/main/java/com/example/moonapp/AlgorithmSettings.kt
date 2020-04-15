package com.example.moonapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_algorithm_settings.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class AlgorithmSettings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_algorithm_settings)

        val data = readFile()
        val dataList = data.split(";")

        if(dataList[0] == "S")
            rb_southern.isChecked = true
        else
            rb_northern.isChecked = true

        when (dataList[1]){
            "Simple" -> {
                rb_simple.isChecked = true
            }
            "Conway"->{
                rb_conway.isChecked = true
            }
            "Trig1"->{
                rb_trig1.isChecked = true
            }
            "Trig2"->{
                rb_trig2.isChecked = true
            }
        }


        rb_hemisphere.setOnCheckedChangeListener{group, checkedId ->
            if(checkedId == R.id.rb_northern)
                saveAlgorithm("N")
            if(checkedId == R.id.rb_southern)
                saveAlgorithm("S")
        }

        rb_algorithm.setOnCheckedChangeListener{group, checkedId ->
            if(checkedId == R.id.rb_simple)
                saveAlgorithm("Simple")
            if(checkedId == R.id.rb_conway)
                saveAlgorithm("Conway")
            if(checkedId == R.id.rb_trig1)
                saveAlgorithm("Trig1")
            if(checkedId == R.id.rb_trig2)
                saveAlgorithm("Trig2")
        }
    }


    fun saveAlgorithm(info: String){
        val data = readFile()
        val dataList = data.split(";")
        var text = ""

        if(info == "S" || info == "N")
            text = info + ";" + dataList[1]
        else
            text = dataList[0] + ";" + info

        val fileName = "algorithm.txt"
        val file = OutputStreamWriter(openFileOutput(fileName, Context.MODE_PRIVATE))

        file.write(text)

        file.flush()
        file.close()

        readFile()
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
//                Toast.makeText(this, line, Toast.LENGTH_LONG).show()
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
