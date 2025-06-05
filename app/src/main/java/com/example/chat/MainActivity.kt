package com.example.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chat.data.Contact
import com.example.chat.ui.presentation.*
import com.example.chat.ui.ui.theme.ChatAppTheme
import com.example.chat.ui.view.BottomNavigationBar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder
import java.net.URLEncoder

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController,
                            onNavigateTosos = { navController.navigate(NavRoutes.sos.route) }        ) }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = NavRoutes.Splash.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(NavRoutes.Home.route) {
                                HomeScreen(
                                    onNavigateToAddContact = { navController.navigate(NavRoutes.AddContact.route) },
                                    onNavigateToContacts = { navController.navigate(NavRoutes.Contacts.route) },
                                    onNavigateToEmergencyHelpline = { navController.navigate(NavRoutes.EmergencyHelpline.route) },
                                    onNavigateTosos = { navController.navigate(NavRoutes.sos.route) },
                                    onNavigateToJourney = { navController.navigate(NavRoutes.Journey.route) }
                                )
                            }
                            composable(NavRoutes.AddContact.route) {
                                AddEmergencyContactScreen(
                                    onNavigateBack = { navController.popBackStack() },
                                    onRequestOtpVerification = { contact ->
                                        val encoded = URLEncoder.encode(Gson().toJson(contact), "UTF-8")
                                        navController.navigate(NavRoutes.OtpVerification.createRoute(encoded))
                                    }
                                )
                            }
                            composable(NavRoutes.Contacts.route) {
                                ContactsScreen(
                                    viewModel = hiltViewModel(),
                                    onNavigateBack = { navController.popBackStack() }
                                )
                            }
                            composable(NavRoutes.Profile.route) {
                                ProfileScreen(
                                    viewModel = hiltViewModel(),
                                    onProfileComplete = {}
                                )
                            }
                            composable(NavRoutes.ProfileOptions.route) {
                                ProfileOptionsScreen(
                                    onBack = { navController.popBackStack() }
                                )
                            }

                            composable(NavRoutes.EditProfile.route) {
                                EditProfileScreen(navController)
                            }


                            composable(NavRoutes.Splash.route) {
                                SplashScreen(onSplashComplete = {
                                    navController.navigate(NavRoutes.Home.route) {
                                        popUpTo(NavRoutes.Splash.route) { inclusive = true }
                                    }
                                })
                            }

                            composable(
                                NavRoutes.OtpVerification.route,
                                arguments = listOf(navArgument("contact") { type = NavType.StringType })
                            ) { backStackEntry ->
                                val contactJson = backStackEntry.arguments?.getString("contact")
                                val contact = Gson().fromJson(
                                    URLDecoder.decode(contactJson, "UTF-8"),
                                    Contact::class.java
                                )
                                OTPVerificationScreen(
                                    contact = contact,
                                    onVerified = { navController.popBackStack() },
                                    onBack = { navController.popBackStack() }
                                )
                            }
                            composable(NavRoutes.EmergencyHelpline.route) {
                                EmergencyHelplineScreen(
                                    onBack = {navController.popBackStack()}
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
