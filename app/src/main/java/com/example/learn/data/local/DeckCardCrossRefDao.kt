package com.example.learn.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DeckCardCrossRefDao {
    @Insert
    suspend fun insertCrossRef(crossRef: DeckCardCrossRef)
    @Delete
    suspend fun deleteCrossRef(crossRef: DeckCardCrossRef)

    @Query("DELETE FROM deck_card_cross_ref WHERE :deckId = deckId")
    suspend fun deleteAll(deckId: String)
}