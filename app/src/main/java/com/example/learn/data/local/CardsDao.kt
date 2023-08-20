package com.example.learn.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CardsDao {

    @Query("SELECT * from cards WHERE id = :cardId")
    fun observeCard(cardId: String): Flow<LocalCard>

    @Query("SELECT * from cards WHERE deckId = :deckId ORDER BY created DESC")
    fun observeCards(deckId: String): Flow<List<LocalCard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(localCard: LocalCard)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(localCard: LocalCard)

    @Query("SELECT * from cards WHERE id = :cardId")
    suspend fun getCard(cardId: String) : LocalCard?

    @Query("SELECT SUBSTR(frontContent, 1, 15) FROM cards WHERE id = :cardId")
    suspend fun getCardTitle(cardId: String): String?

}