package com.example.learn.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DecksDao {

    @Query("SELECT * from decks WHERE deckId = :deckId")
    fun observeDeck(deckId:String): Flow<LocalDeck>

    @Query("SELECT * from decks ORDER BY created DESC")
    fun observeDecks(): Flow<List<LocalDeck>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(localDeck: LocalDeck)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(localDeck: LocalDeck)

    @Query("DELETE FROM decks")
    suspend fun deleteAll()
    @Query("DELETE FROM decks WHERE :deckId = deckId")
    suspend fun deleteById(deckId: String)

    @Query("SELECT * from decks WHERE deckId = :deckId")
    suspend fun getDeck(deckId: String): LocalDeck?

    @Query("SELECT * from decks")
    suspend fun getAll(): List<LocalDeck>

    @Query("SELECT * FROM decks WHERE deckId IN (SELECT deckId FROM deck_card_cross_ref WHERE :cardId = cardId)")
    suspend fun getDeckFromCard(cardId: String): LocalDeck?


}
