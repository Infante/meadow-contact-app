package com.roberto.meadow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roberto.meadow.data.Contact
import com.roberto.meadow.ui.theme.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.HorizontalDivider


@Composable
fun ContactList(
    modifier: Modifier = Modifier,
    contacts: List<Contact> = emptyList(),
    onContactClick: (String) -> Unit,
) {
    if (contacts.isEmpty()) {
        EmptyContactList(modifier = modifier)
    } else {
        PopulatedContactList(contacts = contacts, modifier = modifier, onContactClick = onContactClick)
    }
}
@Composable
private fun PopulatedContactList(
    contacts: List<Contact>,
    modifier: Modifier = Modifier,
    onContactClick: (String) -> Unit,
) {
    val (favorites, nonFavorites) = contacts.partition { it.isFavorite }
    val groupedNonFavorites = nonFavorites.groupBy { it.firstName.first().uppercase() }.toSortedMap()

    LazyColumn(modifier = modifier.padding(16.dp)) {
        item {
            SectionTitle("Favorites")
            ContactGroupBox {
                if (favorites.isEmpty()) {
                    Text(
                        text = "Swipe right on a contact or chat to mark someone as a favorite :)",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Neutral600),
                        modifier = Modifier.padding(12.dp)
                    )
                } else {
                    favorites.forEachIndexed { index, contact ->
                        ContactListItem(
                            contact,
                            onClick = { onContactClick(contact.id) },
                            showDivider = index != favorites.lastIndex
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        groupedNonFavorites.forEach { (letter, contactsInSection) ->
            item {
                SectionTitle(letter)
                ContactGroupBox {
                    contactsInSection.forEachIndexed { index, contact ->
                        ContactListItem(
                            contact,
                            onClick = { onContactClick(contact.id) },
                            showDivider = index != contactsInSection.lastIndex
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}


@Composable
private fun EmptyContactList(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        item {
            SectionTitle("Favorites")
            ContactGroupBox {
                Text(
                    text = "Swipe right on a contact or chat to mark someone as a favorite :)",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Neutral600
                    ),
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            SectionTitle("All contacts")
            ContactGroupBox {
                Text(
                    text = "Click the + in the top right to create a new contact!",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Neutral600
                    ),
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun ContactGroupBox(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(12.dp))
            .padding(vertical = 4.dp),
        content = content
    )
}

@Composable
private fun ContactListItem(
    contact: Contact,
    onClick: () -> Unit,
    showDivider: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "${contact.firstName} ${contact.lastName}",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
        )
    }
    if (showDivider) {
        HorizontalDivider(
            color = Neutral200,
            thickness = 2.dp,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContactListEmptyPreview() {
    ContactList(contacts = emptyList(), onContactClick = {})
}

@Preview(showBackground = true)
@Composable
fun ContactListPopulatedPreview() {
    val sampleContacts = listOf(
        Contact(id = "1", firstName = "Madison", lastName = "Huizinga", phone = "123", isFavorite = true),
        Contact(id = "2", firstName = "Mom", lastName = "Narlanka", phone = "456", isFavorite = true),
        Contact(id = "3", firstName = "Dad", lastName = "Narlanka", phone = "789", isFavorite = true),
        Contact(id = "4", firstName = "Mohnish", lastName = "Narlanka", phone = "101", isFavorite = true),
        Contact(id = "5", firstName = "Arnold", lastName = "", phone = "111", isFavorite = false)
    )
    ContactList(contacts = sampleContacts, onContactClick = {})
}
