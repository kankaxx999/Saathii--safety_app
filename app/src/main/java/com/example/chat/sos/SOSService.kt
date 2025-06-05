package com.example.chat.sos

import android.content.Context
import android.location.Location
import android.util.Log
import com.example.chat.data.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SOSService(
    private val context: Context,
    private val smsHelper: SmsHelper,
    private val contactsRepository: ContactRepository
) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun triggerSOS() {
        coroutineScope.launch {
            try {
                val location: Location? = LocationHelper.getCurrentLocation(context)
                val locationUrl = location?.let {
                    "https://maps.google.com/?q=${it.latitude},${it.longitude}"
                } ?: "Location not available"

                val contacts = contactsRepository.getEmergencyContacts() // should return List<Contact>

                for (contact in contacts) {
                    val phone = contact.phone  // ensure Contact class has a 'phone' property
                    val message = "Emergency! Please help. My current location: $locationUrl"
                    smsHelper.sendSms(phone, message)
                }

                Log.d("SOSService", "SOS SMS sent to ${contacts.size} contacts")

            } catch (e: Exception) {
                Log.e("SOSService", "Failed to send SOS messages: ${e.message}")
            }
        }
    }

}
