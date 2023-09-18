package com.example.learn.data.local

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

    @Delete
    suspend fun delete(localDeck: LocalDeck)

    @Query("SELECT * from decks WHERE deckId = :deckId")
    suspend fun getDeck(deckId: String): LocalDeck?

//    @Query("SELECT * from decks WHERE :cardId IN(cardIds)")
//    suspend fun getDeckByCardId(cardId: String): LocalDeck?

    @Query("SELECT * FROM decks WHERE deckId IN (SELECT deckId FROM deck_card_cross_ref WHERE :cardId = cardId)")
    suspend fun getDeckFromCard(cardId: String): LocalDeck?

    @Query("SELECT cardId FROM deck_card_cross_ref WHERE :deckId = deckId")
    fun getCardIdsStream(deckId: String): Flow<List<String>>
}
