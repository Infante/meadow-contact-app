package com.roberto.meadow.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val firstName: String,
    val lastName: String,
    val phone: String,
    val notes: String? = null,
    val isFavorite: Boolean = false,
    val isBlocked: Boolean = false,
)
