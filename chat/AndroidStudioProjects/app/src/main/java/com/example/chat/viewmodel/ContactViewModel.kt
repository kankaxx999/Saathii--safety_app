package com.example.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat.data.Contact
import com.example.chat.data.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val repository: ContactRepository
) : ViewModel() {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts = _contacts.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    init {
        // Fetch contacts when the ViewModel is created
        getContacts()
    }

    // Fetch contacts from the repository and update the state
    private fun getContacts() {
        viewModelScope.launch {
            repository.getAllContacts().collect { contactList ->
                _contacts.value = contactList
            }
        }
    }

    // Add contact
    fun addContact(contact: Contact) {
        viewModelScope.launch {
            try {
                repository.addContact(contact)
                getContacts() // Refresh the contact list after adding
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    // Delete contact
    fun deleteContact(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteContact(id)
                getContacts() // Refresh the contact list after deleting
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
