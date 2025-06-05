package com.example.chat.sos


import android.content.Context
import android.telephony.SmsManager
import android.util.Log

class SmsHelper(private val context: Context) {

    fun sendSms(phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Log.d("SmsHelper", "SMS sent to $phoneNumber")
        } catch (e: Exception) {
            Log.e("SmsHelper", "Failed to send SMS to $phoneNumber: ${e.message}")
        }
    }
}
