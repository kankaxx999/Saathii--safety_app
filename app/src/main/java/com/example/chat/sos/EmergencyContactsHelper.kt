package com.example.chat.sos


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object EmergencyContactsHelper {

    suspend fun getEmergencyContacts(): List<String> {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return emptyList()
        val db = FirebaseFirestore.getInstance()
        val contactList = mutableListOf<String>()

        return try {
            val snapshot = db.collection("users")
                .document(userId)
                .collection("emergencyContacts")
                .get()
                .await()

            for (doc in snapshot.documents) {
                val phone = doc.getString("phone")
                if (!phone.isNullOrBlank()) {
                    contactList.add(phone)
                }
            }

            contactList
        } catch (e: Exception) {
            Log.e("EmergencyContactsHelper", "Error fetching contacts", e)
            emptyList()
        }
    }
}
