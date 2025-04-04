package com.roberto.meadow.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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

    Column {
        HeaderRow(onAddClick = { showCreateSheet = true })
        SearchField(
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
                onSearchQueryChange(it)
            }
        )
    }

    if (showCreateSheet) {
        CreateContactModalSheet(
            onDismiss = { showCreateSheet = false },
            onCreateContact = onCreateContact
        )
    }
}

@Composable
private fun HeaderRow(
    onAddClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "Contacts", style = MaterialTheme.typography.titleLarge)
        IconButton(onClick = onAddClick) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Contact")
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
            .padding(horizontal = 16.dp),
        singleLine = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateContactModalSheet(
    onDismiss: () -> Unit,
    onCreateContact: (Contact) -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        CreateContactSheet(
            onDismiss = onDismiss,
            onCreateContact = onCreateContact
        )
    }
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
fun HeaderRowPreview() {
    HeaderRow(onAddClick = {})
}

@Preview(showBackground = true)
@Composable
fun SearchFieldPreview() {
    SearchField(query = "Roberto", onQueryChange = {})
}
