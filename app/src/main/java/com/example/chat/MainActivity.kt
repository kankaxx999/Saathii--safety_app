package com.example.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chat.ui.presentation.AddEmergencyContactScreen
import com.example.chat.ui.presentation.ContactsScreen
import com.example.chat.ui.presentation.HomeScreen
import com.example.chat.ui.presentation.ProfileScreen
import com.example.chat.ui.view.BottomNavigationBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            Scaffold(
                bottomBar = {
                    BottomNavigationBar(navController)
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("home") {
                        HomeScreen (
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
                    composable("profile") {
                        ProfileScreen (
                            viewModel = hiltViewModel(),
                            onProfileComplete = {}
                        )
                    }
                }
            }
        }
    }
}