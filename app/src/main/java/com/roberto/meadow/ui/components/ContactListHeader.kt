package com.roberto.meadow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import com.roberto.meadow.ui.theme.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.draw.drawBehind
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.clickable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListHeader(
    onCreateContact: (Contact) -> Unit,
    onSearchQueryChange: (String) -> Unit
) {
    var showCreateSheet by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(WindowInsets.systemBars.asPaddingValues()) // respects status bar
            .drawBehind {
                drawLine(
                    color = Neutral200,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 3.dp.toPx()
                )
            }
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Box(
            modifier = Modifier
                .size(32.dp)
                .background(color = Neutral200, shape = AppShapes.extraLarge),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier.size(19.dp)
            )
        }

        Text(text = "Contacts", style = MaterialTheme.typography.titleLarge)

        Box(
            modifier = Modifier
                .size(32.dp)
                .background(color = Neutral200, shape = AppShapes.extraLarge)
            .clickable { showCreateSheet = true },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Contact",
                modifier = Modifier.size(19.dp)
            )
        }

    }

    if (showCreateSheet) {
        CreateContactModalSheet(
            onDismiss = { showCreateSheet = false },
            onCreateContact = onCreateContact
        )
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
fun SearchFieldPreview() {
    SearchField(query = "Roberto", onQueryChange = {})
}
