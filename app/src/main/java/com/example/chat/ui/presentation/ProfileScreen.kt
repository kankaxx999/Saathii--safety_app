package com.example.chat.ui.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chat.datamodel.UserProfile
import com.example.chat.viewmodel.ProfileViewModel

@Composable


fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onProfileComplete: () -> Unit
) {
    val profile by viewModel.profile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        profile?.let {
            ProfileForm(
                profile = it,
                onProfileChange = { updated -> viewModel.updateProfile(updated) },
                onSaveClick = {
                    viewModel.saveUserProfile(it)
                    if (viewModel.isProfileComplete()) {
                        onProfileComplete()
                    }
                }
            )
        } ?: run {
            ProfileForm(
                profile = UserProfile(),
                onProfileChange = { updated -> viewModel.updateProfile(updated) },
                onSaveClick = {
                    viewModel.saveUserProfile(UserProfile())
                }
            )
        }

        error?.let {
            Text("Error: $it", color = Color.Red)
        }
    }
}
