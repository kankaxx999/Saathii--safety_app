package com.example.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat.datamodel.UserProfile
import com.example.chat.repo.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile: StateFlow<UserProfile?> = _profile

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getUserProfile()
            _isLoading.value = false
            result.onSuccess {
                _profile.value = it ?: UserProfile()
            }.onFailure {
                _error.value = it.message
            }
        }
    }

    fun saveUserProfile(profile: UserProfile) {
        viewModelScope.launch {
            _isLoading.value = true

            val userEmail = auth.currentUser?.email.orEmpty()
            val uid = auth.currentUser?.uid.orEmpty()

            val completeProfile = profile.copy(
                email = userEmail,
                uid = uid
            )

            val result = repository.saveUserProfile(completeProfile)
            _isLoading.value = false

            result.onSuccess {
                _error.value = null
                _profile.value = completeProfile
            }.onFailure {
                _error.value = it.message
            }
        }
    }


    fun updateProfile(updatedProfile: UserProfile) {
        _profile.value = updatedProfile
    }

    fun isProfileComplete(): Boolean {
        return profile.value?.let {
            it.name.isNotBlank() &&
                    it.email.isNotBlank() &&
                    android.util.Patterns.EMAIL_ADDRESS.matcher(it.email).matches() &&
                    it.phone.length == 10
        } ?: false
    }


    fun logout() {
        auth.signOut()
    }
}