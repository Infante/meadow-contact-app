package com.roberto.meadow.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roberto.meadow.viewmodel.ContactViewModel
import com.roberto.meadow.data.Contact

@Composable
fun ContactDetailScreen(
    contactId: String,
    onBackClick: () -> Unit,
    viewModel: ContactViewModel = viewModel()
) {
    val contactState = viewModel.getContactById(contactId).collectAsState(initial = null)
    val contact = contactState.value

    if (contact == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    ContactDetailForm(
        contactId = contactId,
        contact = contact,
        onBackClick = onBackClick,
        onEditContact = {
            contact -> viewModel.updateContact(contact)
        },
        onDeleteContact = {
            contact -> viewModel.deleteContact(contact)
        }
    )
}


@Composable
private fun ContactDetailForm(
    contactId: String,
    contact: Contact,
    onBackClick: () -> Unit,
    onEditContact: (Contact) -> Unit,
    onDeleteContact: (Contact) -> Unit
) {
    var firstName by remember { mutableStateOf(contact.firstName) }
    var lastName by remember { mutableStateOf(contact.lastName) }
    var phone by remember { mutableStateOf(contact.phone) }
    var notes by remember { mutableStateOf(contact.notes ?: "") }
    var isFavourite by remember { mutableStateOf(contact.isFavorite) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Contact") },
            text = { Text("Are you sure you want to delete this contact?") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    onDeleteContact(contact)
                    onBackClick()
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {

            Text("Contact Detail Screen: $contactId", style = MaterialTheme.typography.titleLarge)

            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }

            // Input fields...
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }

                Button(
                    onClick = {
                        val updatedContact = contact.copy(
                            firstName = firstName,
                            lastName = lastName,
                            phone = phone,
                            notes = notes.ifEmpty { null },
                            isFavorite = isFavourite
                        )
                        onEditContact(updatedContact)
                    },
                    enabled = firstName.isNotBlank() && lastName.isNotBlank() && phone.isNotBlank(),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Save")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Checkbox(
                    checked = isFavourite,
                    onCheckedChange = {
                        isFavourite = it
                        val updatedContact = contact.copy(isFavorite = it)
                        onEditContact(updatedContact)
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Favourite")
            }
        }
    }
}