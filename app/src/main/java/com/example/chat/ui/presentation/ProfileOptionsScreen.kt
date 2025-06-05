package com.example.chat.ui.presentation

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.chat.NavRoutes
import com.example.chat.R
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileOptionsScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // User Info Card
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE3EC))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.female_power),
                        contentDescription = "Profile Photo",
                        modifier = Modifier
                            .size(64.dp)
                            .padding(end = 12.dp)
                    )
                    Column {
                        Text("Your Name", fontWeight = FontWeight.Bold)
                        Text("@user", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Settings options
            ProfileOptionItem(Icons.Default.Edit, "Account") { (NavRoutes.EditProfile.route) }
            ProfileOptionItem(Icons.Default.Settings, "Connectivity Settings") { /* TODO */ }
            ProfileOptionItem(Icons.Default.Favorite, "Saathii Health") { /* TODO */ }
            ProfileOptionItem(Icons.Default.Warning, "Self Defence") { /* TODO */ }
            ProfileOptionItem(Icons.Default.Notifications, "My Feedback") { /* TODO */ }
            ProfileOptionItem(Icons.Default.Info, "About Us") { /* TODO */ }
            ProfileOptionItem(Icons.Default.Search, "Help & Support") { /* TODO */ }

            ProfileOptionItem(Icons.Default.Share, "Share App") {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Check out this safety app: https://play.google.com/store/apps/details?id=com.example.chat")
                }
                context.startActivity(Intent.createChooser(intent, "Share via"))
            }

            ProfileOptionItem(Icons.Default.Lock, "Logout") {
                 FirebaseAuth.getInstance().signOut() }

            Spacer(modifier = Modifier.height(20.dp))

            // Privacy Message
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
            ) {
                Text(
                    text = "🔒 Your data is protected. Your privacy is our priority.",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // App Version
            Text(
                text = "Saathii App v1.0.0 • Beta",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ProfileOptionItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, modifier = Modifier.padding(end = 16.dp))
        Text(title, style = MaterialTheme.typography.bodyLarge)
    }
}
