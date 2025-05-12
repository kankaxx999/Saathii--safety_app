
package com.example.chat.data

import kotlinx.coroutines.flow.Flow
interface ContactRepository {
    suspend fun addContact(contact: Contact)
    suspend fun deleteContact(id: Int)
    fun getAllContacts(): Flow<List<Contact>>
}
