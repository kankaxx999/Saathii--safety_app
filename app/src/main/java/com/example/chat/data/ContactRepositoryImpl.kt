
package com.example.chat.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val dao: ContactDao
) : ContactRepository {

    override suspend fun addContact(contact: Contact) {
        dao.insertContact(contact)
    }

    override suspend fun deleteContact(id: Int) {
        dao.deleteContact(id)
    }

    override fun getAllContacts(): Flow<List<Contact>> {
        return dao.getAllContacts()
    }


    override suspend fun updateContact(contact: Contact) {
        dao.updateContact(contact)
    }

    override suspend fun getEmergencyContacts(): List<Contact> {
        return dao.getEmergencyContacts()
    }

}




