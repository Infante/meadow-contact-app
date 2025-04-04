package com.roberto.meadow.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roberto.meadow.data.Contact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateContactSheet(
    onDismiss: () -> Unit,
    onCreateContact: (Contact) -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "New Contact",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ContactFormFields(
            firstName = firstName,
            onFirstNameChange = { firstName = it },
            lastName = lastName,
            onLastNameChange = { lastName = it },
            phone = phone,
            onPhoneChange = { phone = it },
            notes = notes,
            onNotesChange = { notes = it }
        )

        CreateContactActions(
            isValid = firstName.isNotBlank() && lastName.isNotBlank() && phone.isNotBlank(),
            onCancel = onDismiss,
            onCreate = {
                val newContact = Contact(
                    firstName = firstName,
                    lastName = lastName,
                    phone = phone,
                    notes = notes.ifEmpty { null }
                )
                onCreateContact(newContact)
                onDismiss()
            }
        )
    }
}

@Composable
private fun ContactFormFields(
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
            .padding(vertical = 8.dp)
    )

    OutlinedTextField(
        value = lastName,
        onValueChange = onLastNameChange,
        label = { Text("Last Name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )

    OutlinedTextField(
        value = phone,
        onValueChange = onPhoneChange,
        label = { Text("Phone") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )

    OutlinedTextField(
        value = notes,
        onValueChange = onNotesChange,
        label = { Text("Notes") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
private fun CreateContactActions(
    isValid: Boolean,
    onCancel: () -> Unit,
    onCreate: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier.weight(1f)
        ) {
            Text("Cancel")
        }

        Button(
            onClick = onCreate,
            enabled = isValid,
            modifier = Modifier.weight(1f)
        ) {
            Text("Create")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateContactSheetPreview() {
    CreateContactSheet(onDismiss = {}, onCreateContact = {})
}

@Preview(showBackground = true)
@Composable
fun ContactFormFieldsPreview() {
    Surface {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            ContactFormFields(
                firstName = "Jane",
                onFirstNameChange = {},
                lastName = "Doe",
                onLastNameChange = {},
                phone = "123-456-7890",
                onPhoneChange = {},
                notes = "Met at the coffee shop ☕️",
                onNotesChange = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateContactActionsPreview() {
    CreateContactActions(
        isValid = true,
        onCancel = {},
        onCreate = {}
    )
}
