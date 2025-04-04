package com.roberto.meadow.repository

import com.roberto.meadow.data.Contact
import com.roberto.meadow.data.ContactDao
import kotlinx.coroutines.flow.Flow

class ContactRepository(
    private val dao: ContactDao,
) {
    val allContacts: Flow<List<Contact>> = dao.getAllContacts()

    fun getContactById(id: String): Flow<Contact?> {
        return dao.getContactById(id)
    }

    suspend fun insert(contact: Contact) = dao.insert(contact)

    suspend fun update(contact: Contact) = dao.update(contact)

    suspend fun delete(contact: Contact) = dao.delete(contact)
}
