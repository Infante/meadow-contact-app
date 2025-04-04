package com.roberto.meadow.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts ORDER BY firstName ASC")
    fun getAllContacts(): Flow<List<Contact>>

    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getContactById(id: String): Flow<Contact?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)
}
