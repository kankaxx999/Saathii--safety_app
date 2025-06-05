package com.example.chat.ui.presentation


import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chat.datamodel.UserProfile
import com.example.chat.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onProfileComplete: () -> Unit = {}
) {
    val context = LocalContext.current
    val profile by viewModel.profile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        val currentProfile = profile ?: UserProfile()

        ProfileForm(
            profile = currentProfile,
            onProfileChange = { updated -> viewModel.updateProfile(updated) },
            onSaveClick = {

                if (currentProfile.name.isBlank() ||
                    currentProfile.phone.isBlank() ||
                    currentProfile.gender.isBlank() ||
                    currentProfile.dob.isBlank()
                ) {
                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.saveUserProfile(currentProfile)
                    Toast.makeText(context, "Profile saved successfully!", Toast.LENGTH_SHORT).show()

                    if (viewModel.isProfileComplete()) {
                        onProfileComplete()
                    }
                }
            }
        )
    }

    error?.let {
        Toast.makeText(LocalContext.current, "Error: $it", Toast.LENGTH_SHORT).show()
    }
}
