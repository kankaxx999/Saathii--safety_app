package com.example.chat.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): Flow<List<Contact>>

    @Query("DELETE FROM contacts WHERE id = :id")
    suspend fun deleteContact(id: Int)


    @Update
    suspend fun updateContact(contact: Contact)


    @Query("SELECT * FROM contacts WHERE isEmergency = 1")
    suspend fun getEmergencyContacts(): List<Contact>

}