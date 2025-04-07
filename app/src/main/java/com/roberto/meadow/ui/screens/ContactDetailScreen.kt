package com.roberto.meadow.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
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
        onEditContact = { viewModel.updateContact(it) },
        onDeleteContact = { viewModel.deleteContact(it) }
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
    var lastName by remember { mutableStateOf(contact.lastName ?: "") }
    var phone by remember { mutableStateOf(contact.phone) }
    var notes by remember { mutableStateOf(contact.notes ?: "") }
    var isFavourite by remember { mutableStateOf(contact.isFavorite) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                showDeleteDialog = false
                onDeleteContact(contact)
                onBackClick()
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Contact Detail Screen: $contactId", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onBackClick, modifier = Modifier.fillMaxWidth()) {
                Text("Back")
            }

            Spacer(modifier = Modifier.height(16.dp))

            ContactInputFields(
                firstName = firstName,
                onFirstNameChange = { firstName = it },
                lastName = lastName,
                onLastNameChange = { lastName = it },
                phone = phone,
                onPhoneChange = { phone = it },
                notes = notes,
                onNotesChange = { notes = it }
            )

            ContactActionsRow(
                onDeleteClick = { showDeleteDialog = true },
                onSaveClick = {
                    val updated = contact.copy(
                        firstName = firstName,
                        lastName = lastName,
                        phone = phone,
                        notes = notes.ifEmpty { null },
                        isFavorite = isFavourite
                    )
                    onEditContact(updated)
                },
                isSaveEnabled = firstName.isNotBlank() && phone.isNotBlank()
            )

            FavoriteCheckbox(
                isChecked = isFavourite,
                onCheckedChange = {
                    isFavourite = it
                    onEditContact(contact.copy(isFavorite = it))
                }
            )
        }
    }
}

@Composable
private fun ContactInputFields(
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    notes: String,
    onNotesChange: (String) -> Unit
) {
    OutlinedTextField(
        value = firstName,
        onValueChange = onFirstNameChange,
        label = { Text("First Name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
    OutlinedTextField(
        value = lastName,
        onValueChange = onLastNameChange,
        label = { Text("Last Name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
    OutlinedTextField(
        value = phone,
        onValueChange = onPhoneChange,
        label = { Text("Phone") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
    OutlinedTextField(
        value = notes,
        onValueChange = onNotesChange,
        label = { Text("Notes") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}

@Composable
private fun ContactActionsRow(
    onDeleteClick: () -> Unit,
    onSaveClick: () -> Unit,
    isSaveEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onDeleteClick,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Delete")
        }

        Button(
            onClick = onSaveClick,
            enabled = isSaveEnabled,
            modifier = Modifier.weight(1f)
        ) {
            Text("Save")
        }
    }
}

@Composable
private fun FavoriteCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text("Favourite")
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Contact") },
        text = { Text("Are you sure you want to delete this contact?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun ContactInputFieldsPreview() {
    Surface {
        Column(modifier = Modifier.padding(16.dp)) {
            ContactInputFields(
                firstName = "Jane",
                onFirstNameChange = {},
                lastName = "Doe",
                onLastNameChange = {},
                phone = "123-456-7890",
                onPhoneChange = {},
                notes = "School friend",
                onNotesChange = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactActionsRowPreview() {
    ContactActionsRow(
        onDeleteClick = {},
        onSaveClick = {},
        isSaveEnabled = true
    )
}

@Preview(showBackground = true)
@Composable
fun FavoriteCheckboxPreview() {
    FavoriteCheckbox(isChecked = true, onCheckedChange = {})
}

@Preview(showBackground = true)
@Composable
fun DeleteConfirmationDialogPreview() {
    DeleteConfirmationDialog(onConfirm = {}, onDismiss = {})
}