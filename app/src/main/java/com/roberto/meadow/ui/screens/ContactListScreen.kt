package com.roberto.meadow.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.roberto.meadow.ui.components.ContactList
import com.roberto.meadow.ui.components.ContactListHeader
import com.roberto.meadow.viewmodel.ContactViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ContactListScreen(
    onContactClick: (String) -> Unit,
    viewModel: ContactViewModel = viewModel()
) {
    val contacts = viewModel.filteredContacts.collectAsState().value

    Scaffold(
        topBar = { 
            ContactListHeader(
                onCreateContact = { contact ->
                    viewModel.createContact(contact)
                },
                onSearchQueryChange = { query ->
                    viewModel.updateSearchQuery(query)
                }
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            ContactList(
                contacts = contacts,
                onContactClick = onContactClick
            )
        }
    }
}
