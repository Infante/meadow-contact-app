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
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Contacts", style = MaterialTheme.typography.titleLarge)
            IconButton(onClick = { showCreateSheet = true }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Contact")
            }
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onSearchQueryChange(it)
            },
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


    if (showCreateSheet) {
        ModalBottomSheet(
            onDismissRequest = { showCreateSheet = false }
        ) {
            CreateContactSheet(
                onDismiss = { showCreateSheet = false },
                onCreateContact = onCreateContact
            )
        }
    }
}
