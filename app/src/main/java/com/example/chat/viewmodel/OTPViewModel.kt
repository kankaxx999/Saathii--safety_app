package com.example.chat.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import java.util.concurrent.TimeUnit
import android.app.Activity
import com.google.firebase.FirebaseException

class OTPViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _verificationId = MutableStateFlow<String?>(null)
    val verificationId: StateFlow<String?> = _verificationId

    private val _verificationStatus = MutableStateFlow<String?>(null)
    val verificationStatus: StateFlow<String?> = _verificationStatus

    fun sendOtp(phoneNumber: String, activity: Activity) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+$phoneNumber") // phone must include country code, e.g. 919876543210
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    _verificationStatus.value = "Verification completed"

                }

                override fun onVerificationFailed(e: FirebaseException) {
                    _verificationStatus.value = "Verification failed: ${e.message}"
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    _verificationId.value = verificationId
                    _verificationStatus.value = "Code sent"
                }
            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(otpCode: String) {
        val verifId = verificationId.value
        if (verifId != null) {
            val credential = PhoneAuthProvider.getCredential(verifId, otpCode)
            signInWithCredential(credential)
        } else {
            _verificationStatus.value = "Verification ID is null"
        }
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _verificationStatus.value = "Verification successful"
            } else {
                _verificationStatus.value = "Verification failed: ${task.exception?.message}"
            }
        }
    }
}
