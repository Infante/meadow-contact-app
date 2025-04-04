package com.roberto.meadow.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roberto.meadow.data.Contact

@Composable
fun ContactList(
    modifier: Modifier = Modifier,
    contacts: List<Contact> = emptyList(),
    onContactClick: (String) -> Unit,
) {
    when {
        contacts.isEmpty() -> EmptyContactList(modifier = modifier)
        else -> PopulatedContactList(
            contacts = contacts,
            modifier = modifier,
            onContactClick = onContactClick
        )
    }
}

@Composable
private fun PopulatedContactList(
    contacts: List<Contact>,
    modifier: Modifier = Modifier,
    onContactClick: (String) -> Unit
) {
    val (favorites, nonFavorites) = contacts.partition { it.isFavorite }
    val groupedNonFavorites = nonFavorites
        .groupBy { it.firstName.first().uppercase() }
        .toSortedMap()

    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
        if (favorites.isNotEmpty()) {
            item {
                Text("Favorites", style = MaterialTheme.typography.labelLarge)
            }

            items(favorites) { contact ->
                Button(onClick = { onContactClick(contact.id) }) {
                    Text("${contact.firstName} ${contact.lastName}")
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        groupedNonFavorites.forEach { (letter, contactsInSection) ->
            item {
                Text(letter, style = MaterialTheme.typography.labelLarge)
            }

            items(contactsInSection) { contact ->
                Button(onClick = { onContactClick(contact.id) }) {
                    Text("${contact.firstName} ${contact.lastName}")
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun EmptyContactList(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        item {
            Text(
                text = "No contacts yet",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Text(
                text = "Add contacts using the + button above",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
