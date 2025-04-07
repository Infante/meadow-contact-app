package com.roberto.meadow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.roberto.meadow.data.Contact
import androidx.compose.material3.TextFieldDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListHeader(
    onCreateContact: (Contact) -> Unit,
    onSearchQueryChange: (String) -> Unit
) {
    var showCreateSheet by remember { mutableStateOf(false) }
    var showSearch by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }


    Column {
        HeaderBar(
            startContent = {
                HeaderButton(
                    onClick = { showSearch = !showSearch },
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

        if (showSearch) {
            SearchField(
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                    onSearchQueryChange(it)
                },
                onClear = {
                    showSearch = false
                }
            )
        }
    }

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
fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            },
            trailingIcon = {
                IconButton(onClick = onClear) {
                    Icon(Icons.Filled.Close, contentDescription = "Close search")
                }
            },
            placeholder = { Text("Search contacts...") },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
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
    SearchField(query = "Roberto", onQueryChange = {}, onClear = {})
}
