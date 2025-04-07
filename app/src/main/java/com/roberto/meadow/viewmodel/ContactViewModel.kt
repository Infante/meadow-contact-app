package com.roberto.meadow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.roberto.meadow.data.Contact
import com.roberto.meadow.data.ContactDatabase
import com.roberto.meadow.repository.ContactRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class ContactViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val dao = ContactDatabase.getDatabase(application).contactDao()
    private val repository = ContactRepository(dao)
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery

    val contacts =
        repository.allContacts
            .map { it.sortedBy { contact -> contact.firstName } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredContacts = combine(contacts, searchQuery) { contacts, query ->
        if (query.isBlank()) {
            contacts
        } else {
            contacts.filter {
                it.firstName.contains(query, ignoreCase = true) ||
                        it.lastName.orEmpty().contains(query, ignoreCase = true) ||
                        it.phone.contains(query)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun createContact(contact: Contact) {
        viewModelScope.launch {
            repository.insert(contact)
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            repository.update(contact)
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            repository.delete(contact)
        }
    }

    fun getContactById(id: String): Flow<Contact?> {
        return repository.getContactById(id)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
