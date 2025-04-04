package com.roberto.meadow.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roberto.meadow.data.Contact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListHeader(
    onCreateContact: (Contact) -> Unit
) {
    var showCreateSheet by remember { mutableStateOf(false) }

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
        Text(text = "Contacts", style = MaterialTheme.typography.titleLarge)
        IconButton(onClick = { showCreateSheet = true }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Contact")
        }
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
