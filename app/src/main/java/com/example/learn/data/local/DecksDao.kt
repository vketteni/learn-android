package com.example.learn.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DecksDao {

    @Query("SELECT * from decks WHERE id = :deckId")
    fun observeDeck(deckId:String): Flow<LocalDeck>

    @Query("SELECT * from decks ORDER BY created DESC")
    fun observeDecks(): Flow<List<LocalDeck>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(localDeck: LocalDeck)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(localDeck: LocalDeck)

    @Query("SELECT * from decks WHERE id = :deckId")
    suspend fun getDeck(deckId: String): LocalDeck?
}