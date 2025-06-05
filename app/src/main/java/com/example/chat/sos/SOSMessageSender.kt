package com.example.chat.sos

import android.content.Context
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SOSMessageSender {

    fun sendSOSMessage(context: Context, locationUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val contacts = EmergencyContactsHelper.getEmergencyContacts()

                if (contacts.isEmpty()) {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(context, "No emergency contacts found", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                val message = "This is an emergency! My live location: $locationUrl"

                val smsManager = SmsManager.getDefault()
                for (number in contacts) {
                    smsManager.sendTextMessage(number, null, message, null, null)
                    Log.d("SOS", "Message sent to $number")
                }

                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, "SOS message sent to emergency contacts", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("SOS", "Failed to send SOS message", e)
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, "Failed to send SOS message", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
