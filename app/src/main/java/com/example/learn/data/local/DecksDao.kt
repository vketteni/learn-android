package com.example.learn.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DecksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(localDeck: LocalDeck)

    @Update
    suspend fun update(localDeck: LocalDeck)

    @Query("DELETE FROM decks")
    suspend fun deleteDecks()

    @Query("DELETE FROM decks WHERE id = :deckId")
    suspend fun deleteDeck(deckId:String)

    @Query("SELECT * from decks WHERE id = :deckId")
    fun observeDeck(deckId:String): Flow<LocalDeck>

    @Query("SELECT * from decks ORDER BY created DESC")
    fun observeDecks(): Flow<List<LocalDeck>>

    @Query("SELECT * from decks WHERE id = :deckId")
    fun getDeck(deckId: String): LocalDeck?

    @Query("SELECT * from decks ORDER BY created DESC")
    fun getDecks(): List<LocalDeck>
}