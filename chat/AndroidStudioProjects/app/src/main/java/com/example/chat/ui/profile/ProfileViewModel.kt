package com.example.chat.ui.profile


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                _profile.value = it
            }.onFailure {
                _error.value = it.message
            }
        }
    }

    fun saveUserProfile(profile: UserProfile) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.saveUserProfile(profile)
            _isLoading.value = false
            result.onSuccess {
                _error.value = null
                _profile.value = profile
            }.onFailure {
                _error.value = it.message
            }
        }
    }

    fun updateProfile(updatedProfile: UserProfile) {
        _profile.value = updatedProfile
    }

    fun isProfileComplete(): Boolean {
        return _profile.value?.let {
            it.name.isNotBlank() && it.email.isNotBlank() && it.phone.isNotBlank()
        } ?: false
    }

    fun logout() {
        auth.signOut()
    }
}
