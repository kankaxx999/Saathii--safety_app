package com.example.chat.ui.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chat.data.Contact
import com.example.chat.viewmodel.ContactViewModel
import com.example.chat.viewmodel.OTPViewModel

@Composable
fun OTPVerificationScreen(
    contact: Contact,
    otpViewModel: OTPViewModel = hiltViewModel(),
    contactViewModel: ContactViewModel = hiltViewModel(),
    onVerified: () -> Unit,
    onBack: () -> Unit
) {
    val verificationStatus by otpViewModel.verificationStatus.collectAsState()
    var otp by remember { mutableStateOf("") }
    val context = LocalContext.current
    val activity = context as? android.app.Activity

    LaunchedEffect(contact.phone) {
        activity?.let {
            otpViewModel.sendOtp(contact.phone, it)
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Verify contact: ${contact.name}", style = MaterialTheme.typography.titleMedium)
        Text(text = "Phone: ${contact.phone}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = otp,
            onValueChange = { otp = it },
            label = { Text("Enter OTP") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { otpViewModel.verifyOtp(otp) }) {
            Text("Verify")
        }

        Spacer(modifier = Modifier.height(16.dp))

        verificationStatus?.let { status ->
            Text(text = status)
            if (status == "Verification successful") {
                val verifiedContact = contact.copy(verified = true)
                LaunchedEffect(verifiedContact) {
                    contactViewModel.updateContact(verifiedContact)
                    onVerified()
                }
            }

        }
            }
        }

