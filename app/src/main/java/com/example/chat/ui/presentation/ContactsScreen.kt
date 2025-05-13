package com.example.chat.ui.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat.viewmodel.ContactViewModel

@Composable
fun ContactsScreen(viewModel: ContactViewModel, onNavigateBack: () -> Unit) {
    val contacts by viewModel.contacts.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Saved Contacts", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        if (contacts.isEmpty()) {
            Text("No contacts saved.")
        } else {
            contacts.forEach { contact ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Name: ${contact.name}")
                        Text(text = "Phone: ${contact.phone}")
                    }
                    IconButton(onClick = { viewModel.deleteContact(contact.id) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Contact"
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onNavigateBack) {
            Text("Back")
        }
    }
}