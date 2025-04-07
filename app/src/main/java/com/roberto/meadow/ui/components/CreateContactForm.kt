package com.roberto.meadow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.roberto.meadow.data.Contact
import com.roberto.meadow.ui.theme.Neutral100
import com.roberto.meadow.ui.theme.Neutral200
import com.roberto.meadow.ui.theme.Neutral600


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateContactForm( onSubmit: (Contact) -> Unit = {}, onDismiss: () -> Unit = {}) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val isValid = firstName.isNotBlank() && phone.isNotBlank()


    fun handleSubmit() {
        val newContact = Contact(
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            notes = notes
        )
        onSubmit(newContact)

        // Reset all fields
        firstName = ""
        lastName = ""
        phone = ""
        notes = ""

        // Dismiss the sheet
        onDismiss()
    }

    HeaderBar(
        startContent = {
            HeaderButton(
                onClick = onDismiss,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        modifier = Modifier.size(19.dp)
                    )
                }
            )
        },
        titleContent = {
            Text("Create Contact", style = MaterialTheme.typography.titleLarge)
        },
        endContent = {
           HeaderButton (
               onClick = { handleSubmit() },
               icon = {
                   Icon(
                       imageVector = Icons.Filled.Check,
                       contentDescription = "Create Contact",
                       modifier = Modifier.size(19.dp),
                       MaterialTheme.colorScheme.onPrimary
                   )
               },
               enabled = isValid,
               backgroundColor = MaterialTheme.colorScheme.primary
           )
        },
        systemBarsPadding=false
    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color= Neutral100)
            .padding(16.dp)
    ) {

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
    val customTextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Neutral200,
        unfocusedBorderColor = Neutral200,
        focusedLabelColor = Neutral600,
        unfocusedLabelColor = Neutral600,
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        focusedPlaceholderColor = Neutral600,
        unfocusedPlaceholderColor = Neutral600,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White
    )

    OutlinedTextField(
        value = firstName,
        onValueChange = onFirstNameChange,
        label = { Text("First Name") },
        colors = customTextFieldColors,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )

    OutlinedTextField(
        value = lastName,
        onValueChange = onLastNameChange,
        label = { Text("Last Name") },
        colors = customTextFieldColors,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )

    OutlinedTextField(
        value = phone,
        onValueChange = onPhoneChange,
        label = { Text("Phone") },
        colors = customTextFieldColors,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )

    OutlinedTextField(
        value = notes,
        onValueChange = onNotesChange,
        label = { Text("Notes") },
        colors = customTextFieldColors,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        singleLine = false,
        minLines = 5,
    )
}


@Preview(showBackground = true)
@Composable
fun CreateContactFormPreview() {
    CreateContactForm(
        onSubmit = {},
        onDismiss = {}
    )
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


