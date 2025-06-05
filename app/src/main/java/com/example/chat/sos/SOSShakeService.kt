package com.example.chat.sos

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import com.example.chat.sos.LocationHelper
import com.example.chat.sos.SOSMessageSender
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SOSShakeService : Service(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private var shakeCount = 0
    private var lastShakeTime = 0L
    private var upCount = 0
    private var downCount = 0

    private var isSosTriggered = false

    override fun onCreate() {
        super.onCreate()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        accelerometer?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        Toast.makeText(this, "Shake detection started", Toast.LENGTH_SHORT).show()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return

        val y = event.values[1]  // Use y-axis for up/down movement
        val currentTime = System.currentTimeMillis()

        if (currentTime - lastShakeTime > 300) {
            lastShakeTime = currentTime

            when {
                y < -6 -> upCount++  // upward movement
                y > 6 -> downCount++ // downward movement
            }

            if (upCount >= 2 && downCount >= 3 && !isSosTriggered) {
                isSosTriggered = true
                triggerSOS()

                // Reset counts after triggering
                upCount = 0
                downCount = 0

                Handler(Looper.getMainLooper()).postDelayed({
                    isSosTriggered = false
                }, 5000)
            }
        }
    }

    private fun triggerSOS() {
        Toast.makeText(this, "SOS Triggered! Sending Alerts...", Toast.LENGTH_LONG).show()

        CoroutineScope(Dispatchers.IO).launch {
            val location = LocationHelper.getCurrentLocation(this@SOSShakeService)
            if (location != null) {
                val lat = location.latitude
                val lon = location.longitude
                val locationUrl = "https://maps.google.com/?q=$lat,$lon"

                SOSMessageSender.sendSOSMessage(this@SOSShakeService, locationUrl)
            } else {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(
                        this@SOSShakeService,
                        "Failed to fetch location.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
