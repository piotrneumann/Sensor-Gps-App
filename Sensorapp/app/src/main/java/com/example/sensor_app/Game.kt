package com.example.sensor_app

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import java.nio.file.Files.size
import java.util.*
import kotlin.concurrent.schedule


class Game : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    private var xPos = 0.0f
    private var xAccel = 0.0f
    private var xVel = 0.0f
    private var yPos = 0.0f
    private var yAccel = 0.0f
    private var yVel = 0.0f;
    private var xMax = 0.0f
    private var yMax = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels
        xMax = width - 100F
        yMax = height - 100F
        xPos = xMax / 2
        yPos = yMax / 2
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        textViewxd.text = width.toString().plus(" ").plus(height).plus(" ")
        Log.d("DUPA", "DUPPPPPPPPPPPPPPPPA")


//        val animation: TranslateAnimation = TranslateAnimation(0.0f, 50.0f, 0.0f, 0.0f)
//        animation.duration = 700
////        animation.repeatCount = 5
////        animation.repeatMode = 2
//        animation.fillAfter = true
//        imageV.startAnimation(animation)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }


    override fun onSensorChanged(event: SensorEvent) {

        xAccel = -event.values[0];
        yAccel = -event.values[1];
        updateBall();
        textViewxd.text = xAccel.toString().plus(" ").plus(yAccel)
        updateBall()
    }

    override fun onResume() {
        super.onResume()
        sensor?.also { light ->
            sensorManager.registerListener(
                this,
                light,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    private fun updateBall() {

        val frameTime = 2.266f
        xVel += xAccel * frameTime
        yVel += yAccel * frameTime
        val xS: Float = xVel / 2 * frameTime
        val yS: Float = yVel / 2 * frameTime
        xPos -= xS
        yPos -= yS

        if (xPos > 1520F) {
            xPos = 1520F
        }

        if (xPos < 0F) {
            xPos = 0F
        }

        if (yPos > 980F) {
            yPos = 980F
        }

        if (yPos < 0F) {
            yPos = 0F
        }

        imageV.x = yPos
        imageV.y = xPos

        Log.d("Ball", imageV.x.toString().plus(" ").plus(imageV.y))
        Log.d("Poz", xPos.toString().plus(" ").plus(yPos))
        Log.d("MAX", xMax.toString().plus(" ").plus(yMax))
    }

}


