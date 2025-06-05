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
        getContacts()
    }

    private fun getContacts() {
        viewModelScope.launch {
            repository.getAllContacts().collect {
                _contacts.value = it
            }
        }
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch {
            repository.addContact(contact)
            getContacts()
        }
    }

    fun deleteContact(id: Int) {
        viewModelScope.launch {
            repository.deleteContact(id)
            getContacts()
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            repository.updateContact(contact)
            getContacts()
        }
    }
}
