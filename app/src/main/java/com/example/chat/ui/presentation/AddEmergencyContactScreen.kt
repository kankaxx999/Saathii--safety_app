package com.example.chat.ui.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chat.data.Contact
import com.example.chat.viewmodel.ContactViewModel
import kotlinx.coroutines.launch

@Composable
fun AddEmergencyContactScreen(
    onNavigateBack: () -> Unit,
    onRequestOtpVerification: (Contact) -> Unit,
    viewModel: ContactViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Add Emergency Contact", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                    phoneError = it.length != 10 || it.any { c -> !c.isDigit() }
                },
                label = { Text("Phone Number") },
                isError = phoneError,
                supportingText = {
                    if (phoneError) Text("Enter a valid 10-digit phone number")
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    phoneError = phone.length != 10 || phone.any { !it.isDigit() }
                    if (name.isNotBlank() && !phoneError) {
                        val contact = Contact(name = name, phone = phone)
                        viewModel.addContact(contact)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Contact saved. Proceed to verify.")
                        }
                        onRequestOtpVerification(contact)
                    }
                }
            ) {
                Text("Save Contact")
            }
        }
    }
}
