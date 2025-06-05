package com.example.chat.ui.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.chat.NavRoutes


@Composable
fun BottomNavigationBar(navController: NavController,
                         onNavigateTosos: () -> Unit)
    {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box {
        NavigationBar(
            containerColor = Color(0xFFFFE3EC), // Light pink background
            tonalElevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = currentRoute == NavRoutes.Home.route,
                onClick = { navController.navigate(NavRoutes.Home.route) }
            )

            NavigationBarItem(
                icon = { Icon(Icons.Default.LocationOn, contentDescription = "Live Location") },
                label = { Text("Location") },
                selected = false,
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=My+Location"))
                    navController.context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.width(60.dp)) // Space for floating SOS button

            NavigationBarItem(
                icon = { Icon(Icons.Default.Call, contentDescription = "Contacts") },
                label = { Text("Contacts") },
                selected = currentRoute == NavRoutes.Contacts.route,
                onClick = { navController.navigate(NavRoutes.Contacts.route) }
            )

            NavigationBarItem(
                icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                label = { Text("Profile") },
                selected = currentRoute == NavRoutes.ProfileOptions.route,
                onClick = { navController.navigate(NavRoutes.ProfileOptions.route) }

            )
        }


        FloatingActionButton(
            onClick = onNavigateTosos,
            containerColor = Color.Red,
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-20).dp)
                .size(56.dp)
                .shadow(10.dp, CircleShape)
        ) {
            Icon(Icons.Default.Warning, contentDescription = "SOS")
        }
    }
}
