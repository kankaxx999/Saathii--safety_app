package com.example.chat.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController,
    onNavigateToAddContact: () -> Unit,
    onNavigateToContacts: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Sathii",
            fontSize = 28.sp,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"))
                context.startActivity(intent)
            }) {
                Icon(imageVector = Icons.Default.Warning, contentDescription = "SOS")
                Spacer(modifier = Modifier.width(8.dp))
                Text("SOS")
            }

            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=My+Location"))
                context.startActivity(intent)
            }) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Live Location")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share live location")
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onNavigateToAddContact,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Add Contact")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Emergency Contact")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onNavigateToContacts,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.Call, contentDescription = "View Contacts")
            Spacer(modifier = Modifier.width(8.dp))
            Text("View Contacts")
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomNavigationBar(navController)
    }
}

@Composable
fun StyledButton(text: String, query: String) {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$query"))
            context.startActivity(intent)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text)
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Arrow")
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = true,
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "Live Location") },
            label = { Text("Live Location") },
            selected = false,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=My+Location"))
                navController.context.startActivity(intent)
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Call, contentDescription = "Contacts") },
            label = { Text("Contacts") },
            selected = false,
            onClick = { navController.navigate("contacts") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = false,
            onClick = { /* future screen */ }
        )
    }
}
