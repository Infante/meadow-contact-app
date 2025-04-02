package com.roberto.meadow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.roberto.meadow.data.Contact
import com.roberto.meadow.data.ContactDatabase
import com.roberto.meadow.repository.ContactRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = ContactDatabase.getDatabase(application).contactDao()
    private val repository = ContactRepository(dao)

    // StateFlow that always has the current list of contacts
    val contacts = repository.allContacts
        .map { it.sortedBy { contact -> contact.firstName } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addContact(contact: Contact) {
        viewModelScope.launch {
            repository.insert(contact)
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            repository.delete(contact)
        }
    }
}
