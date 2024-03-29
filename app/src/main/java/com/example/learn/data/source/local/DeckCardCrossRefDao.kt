package com.example.learn.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckCardCrossRefDao {
    @Query("SELECT * FROM deck_card_cross_ref")
    suspend fun getAll(): List<DeckCardCrossRef>
    @Insert
    suspend fun insertCrossRef(crossRef: DeckCardCrossRef)
    @Query("DELETE FROM deck_card_cross_ref")
    suspend fun deleteAll()
    @Delete
    suspend fun deleteCrossRef(crossRef: DeckCardCrossRef)

    @Query("DELETE FROM deck_card_cross_ref WHERE :deckId = deckId")
    suspend fun deleteByDeck(deckId: String)

    @Query("SELECT cardId FROM deck_card_cross_ref WHERE :deckId = deckId")
    fun getReferencedCardIdsStream(deckId: String): Flow<List<String>>
}