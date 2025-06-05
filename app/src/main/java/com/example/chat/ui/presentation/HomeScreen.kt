package com.example.chat.ui.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.chat.R
import com.example.chat.ui.view.StyledButton
import kotlinx.coroutines.delay

@Composable
fun CardAction(icon: ImageVector, label: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(150.dp, 120.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = label, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
        }
    }
}

@Composable
fun HomeScreen(
    onNavigateToAddContact: () -> Unit,
    onNavigateToContacts: () -> Unit,
    onNavigateToEmergencyHelpline: () -> Unit,
    onNavigateTosos: () -> Unit,
    onNavigateToJourney: () -> Unit
) {
    val context = LocalContext.current

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFFFFC1E3), Color.White)
    )

    var showHeader by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(200)
        showHeader = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // App Logo and Animated Title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.female_power),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 8.dp)
            )

            AnimatedVisibility(
                visible = showHeader,
                enter = fadeIn(animationSpec = tween(800)) + scaleIn(initialScale = 0.8f)
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFE91E63), shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Saathii",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Quick Actions Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CardAction(
                icon = Icons.Default.Warning,
                label = "SOS"
            ) { onNavigateTosos() }

            CardAction(
                icon = Icons.Default.LocationOn,
                label = "Share live\nlocation"
            ) {
                val uri = Uri.parse("geo:0,0?q=Emergency+Location")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.google.android.apps.maps")
                context.startActivity(intent)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Add Contact Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Add Emergency Contacts", style = MaterialTheme.typography.titleMedium)
                Text("Add close people and friends for SOS", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onNavigateToAddContact) {
                    Icon(Icons.Default.AccountBox, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Add Contacts")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // View Contact Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Your Emergency Contacts", style = MaterialTheme.typography.titleMedium)
                Text("View and manage your saved emergency contacts.", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onNavigateToContacts) {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Contacts")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("View Contacts")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Start Journey Card (Now Clickable)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToJourney() },
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE3EC))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Start a journey ✈️", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
                    Text("Enter your destination, and the app will track your route in real-time.", style = MaterialTheme.typography.bodySmall)
                }
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Start", tint = MaterialTheme.colorScheme.primary)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Nearby Buttons
        StyledButton("Police station near me", "police station near me")
        StyledButton("Hospital near me", "hospital near me")
        StyledButton("Emergency Helpline", null.toString(), onClick = onNavigateToEmergencyHelpline)

        Spacer(modifier = Modifier.height(60.dp))
    }
}
