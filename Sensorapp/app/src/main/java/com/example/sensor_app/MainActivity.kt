package com.example.sensor_app

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.stream.Collector


class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        val sen = deviceSensors.map { it.name }
        listview.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sen))

        button.setOnClickListener { view ->
            Log.d("TAG", "message")
            print("DUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUPA")
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            val intent = Intent(this, Gps::class.java)
            startActivity(intent)
        }
    }
}
