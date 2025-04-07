package com.roberto.meadow.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roberto.meadow.ui.components.*
import com.roberto.meadow.ui.theme.*
import com.roberto.meadow.viewmodel.ContactViewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ContactDetailScreen(
    contactId: String,
    onBackClick: () -> Unit,
    viewModel: ContactViewModel = viewModel()
) {
    val contactState = viewModel.getContactById(contactId).collectAsState(initial = null)
    val contact = contactState.value
    var showEditSheet by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (contact == null) {
       Text("Contact not found")
        return
    }

    Scaffold(
        topBar = {
            HeaderBar(
                startContent = {
                    HeaderButton(
                        onClick = onBackClick,
                        icon = {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(19.dp))
                        }
                    )
                },
                titleContent = {
                    Text(
                        text = "${contact.firstName} ${contact.lastName.orEmpty()}".trim(),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                endContent = {
                    HeaderButton(
                        onClick = { showEditSheet = true },
                        icon = {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit", modifier = Modifier.size(19.dp))
                        }
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                MainActionButton(
                    label = "Call",
                    icon = { Icon(Icons.Filled.Phone, contentDescription = "Call", modifier = Modifier.size(24.dp)) },
                    onClick = { /* TODO: Implement call action */ },
                    modifier = Modifier.weight(1f),
                    border = BorderStroke(3.dp, Color(0xFF006400)),

                )

                MainActionButton(
                    backgroundColor = Color.White,
                    label = if (contact.isFavorite) "Unfavorite" else "Favorite",
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favorite",
                            tint = if (contact.isFavorite) Neutral600 else Yellow,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    onClick = {
                        viewModel.updateContact(contact.copy(isFavorite = !contact.isFavorite))
                    },
                    modifier = Modifier.weight(1f),
                    border = BorderStroke(3.dp, if (contact.isFavorite) Neutral200 else Warning600),
                    contentColor = Black
                )
            }

            ContactInfoCard(label = "Phone", value = contact.phone)
            ContactInfoCard(label = "Notes", value = contact.notes.orEmpty())

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = { /* TODO: Implement block action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Black),
                    modifier = Modifier.weight(1f),
                    border = BorderStroke(3.dp, Black),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text("Block",
                        style= MaterialTheme.typography.bodyLarge
                    )
                }

                Button(
                    onClick = { showDeleteDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Danger600),
                    modifier = Modifier.weight(1f),
                    border = BorderStroke(3.dp, Danger800),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text("Delete",
                        style= MaterialTheme.typography.bodyLarge
                    )
                }
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

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                showDeleteDialog = false
                viewModel.deleteContact(contact)
                onBackClick()
            },
            onDismiss = { showDeleteDialog = false }
        )
    }
}


@Composable
fun MainActionButton(
    label: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    border: BorderStroke? = null,
    contentColor: Color = Color.White,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor, contentColor = contentColor),
        border = border,
        shape = MaterialTheme.shapes.large
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            icon()
            Text(label, style = MaterialTheme.typography.bodyLarge)
        }
    }
}


@Composable
fun ContactInfoCard(label: String, value: String) {
    val shape = RoundedCornerShape(16.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(3.dp, Neutral200, shape)
            .clip(shape)
            .background(White)
            .padding(16.dp)
    ) {
        Column {
            Text(
                label,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Neutral600,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(value, style = MaterialTheme.typography.bodyLarge)
        }
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
fun MainActionButtonPreview() {
    MainActionButton(
        label = "Call",
        icon = { Icon(Icons.Filled.Phone, contentDescription = "Call", modifier = Modifier.size(24.dp)) },
        onClick = {},
        border = BorderStroke(3.dp, Color(0xFF006400)),
        backgroundColor = Color(0xFF008000),
    )
}

@Preview(showBackground = true)
@Composable
fun ContactInfoCardPreview() {
    Surface {
        ContactInfoCard(label = "Phone", value = "+1 (555) 123-4567")
    }
}


@Preview(showBackground = true)
@Composable
fun DeleteConfirmationDialogPreview() {
    DeleteConfirmationDialog(onConfirm = {}, onDismiss = {})
}