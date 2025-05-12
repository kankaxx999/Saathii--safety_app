package com.example.chat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chat.data.Contact
import com.example.chat.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val contactViewModel: ContactViewModel = hiltViewModel()

            val navController = rememberNavController()

            Scaffold(
                bottomBar = {
                    com.example.chat.ui.BottomNavigationBar(navController)
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("home") {
                        HomeScreen(
                            onNavigateToAddContact = { navController.navigate("add_contact") },
                            onNavigateToContacts = { navController.navigate("contacts") }
                        )
                    }
                    composable("add_contact") {
                        AddEmergencyContactScreen(
                            onNavigateBack = { navController.popBackStack() },
                            viewModel = hiltViewModel()
                        )
                    }
                    composable("contacts") {
                        ContactsScreen(
                            viewModel = hiltViewModel(),
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
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
                Text(text = "SOS")
            }

            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=My+Location"))
                context.startActivity(intent)
            }) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Live Location")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Share live location")
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onNavigateToAddContact,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Add Contact")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Add Emergency Contact")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onNavigateToContacts,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.Call, contentDescription = "View Contacts")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "View Contacts")
        }

        Spacer(modifier = Modifier.height(30.dp))

        com.example.chat.ui.StyledButton("Police station near me", "police station near me")
        com.example.chat.ui.StyledButton("Hospital near me", "hospital near me")
        com.example.chat.ui.StyledButton("Emergency Helpline", "emergency helpline")
    }
}

@Composable
fun AddEmergencyContactScreen(onNavigateBack: () -> Unit, viewModel: ContactViewModel) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && phone.isNotBlank()) {
                    viewModel.addContact(Contact(name = name, phone = phone))
                    onNavigateBack()
                }
            }
        ) {
            Text("Save Contact")
        }
    }
}

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
            Text(text = text)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Arrow"
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "Live Location") },
            label = { Text("Live Location") },
            selected = false,
            onClick = {
                // Open Google Maps with the current location
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=My+Location"))
                navController.context.startActivity(intent)
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Call, contentDescription = "Contacts") },
            label = { Text("Contacts") },
            selected = currentRoute == "contacts",
            onClick = { navController.navigate("contacts") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = false,
            onClick = { /* Future: Add profile screen */ }
        )
    }
}
