package com.roberto.meadow.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roberto.meadow.data.Contact
import com.roberto.meadow.ui.components.*
import com.roberto.meadow.viewmodel.ContactViewModel
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ContactDetailScreen(
    contactId: String,
    onBackClick: () -> Unit,
    viewModel: ContactViewModel = viewModel()
) {
    val contactState = viewModel.getContactById(contactId).collectAsState(initial = null)
    val contact = contactState.value
    var showEditSheet by remember { mutableStateOf(false) }

    if (contact == null) {
        Text("Contact not found")
        return
    }

    Scaffold(
        topBar = {
            HeaderBar(
                startContent = {
                    HeaderButton(
                        onClick = {
                            onBackClick()
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Navigate previous",
                                modifier = Modifier.size(19.dp)
                            )
                        }
                    )
                },
                titleContent = {
                    Text(text = "${contact.firstName} ${contact.lastName.orEmpty()}".trim(), style = MaterialTheme.typography.titleLarge)

                },
                endContent = {
                    HeaderButton(
                        onClick = { showEditSheet = true },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Edit Contact",
                                modifier = Modifier.size(19.dp)
                            )
                        }
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("Phone: ${contact.phone}")
            Spacer(Modifier.height(4.dp))
            Text("Notes: ${contact.notes.orEmpty()}")
            Spacer(Modifier.height(16.dp))

            FavoriteCheckbox(
                isChecked = contact.isFavorite,
                onCheckedChange = { viewModel.updateContact(contact.copy(isFavorite = it)) }
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { viewModel.deleteContact(contact); onBackClick() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Delete Contact")
            }
        }
    }

    if (showEditSheet) {
        BottomSheetContainer(onDismiss = { showEditSheet = false }) {
            ContactForm(
                initialContact = contact,
                onSubmit = {
                    viewModel.updateContact(it)
                    showEditSheet = false
                },
                onDismiss = { showEditSheet = false }
            )
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
fun FavoriteCheckboxPreview() {
    FavoriteCheckbox(isChecked = true, onCheckedChange = {})
}

@Preview(showBackground = true)
@Composable
fun DeleteConfirmationDialogPreview() {
    DeleteConfirmationDialog(onConfirm = {}, onDismiss = {})
}