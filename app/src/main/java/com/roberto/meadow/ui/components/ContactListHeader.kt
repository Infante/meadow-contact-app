package com.roberto.meadow.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.roberto.meadow.data.Contact


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListHeader(
    onCreateContact: (Contact) -> Unit,
    onSearchQueryChange: (String) -> Unit
) {
    var showCreateSheet by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    HeaderBar(
        startContent = {
            HeaderButton(
                onClick = { /* TODO: Implement search */ },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        modifier = Modifier.size(19.dp)
                    )
                }
            )
        },
        titleContent = {
            Text(text = "Contacts", style = MaterialTheme.typography.titleLarge)
        },
        endContent = {

            HeaderButton(
                onClick = { showCreateSheet = true },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Contact",
                        modifier = Modifier.size(19.dp)
                    )
                }
            )
        },
        systemBarsPadding = true
    )

    if (showCreateSheet) {
        BottomSheetContainer(
            onDismiss = { showCreateSheet = false }
        ) {
            ContactForm(
                onSubmit = onCreateContact,
                onDismiss = { showCreateSheet = false }

            )
        }
    }
}


@Composable
private fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search"
            )
        },
        placeholder = { Text("Search contacts...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun ContactListHeaderPreview() {
    ContactListHeader(
        onCreateContact = {},
        onSearchQueryChange = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SearchFieldPreview() {
    SearchField(query = "Roberto", onQueryChange = {})
}
