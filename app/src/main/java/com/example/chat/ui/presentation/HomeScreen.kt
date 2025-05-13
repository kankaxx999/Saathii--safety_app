package com.example.chat.ui.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat.ui.view.StyledButton
import org.intellij.lang.annotations.JdkConstants

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

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sathii",
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
        }

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

        StyledButton("Police station near me", "police station near me")
        StyledButton("Hospital near me", "hospital near me")
        StyledButton("Emergency Helpline", "emergency helpline")
    }
}