package com.example.learn.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CardsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(localCard: LocalCard)

    @Update
    suspend fun update(localCard: LocalCard)

    @Delete
    suspend fun delete(localCard: LocalCard)

    @Query("SELECT * from cards WHERE id = :cardId")
    fun getCard(cardId: String) : LocalCard?

    @Query("SELECT * from cards WHERE id = :cardId")
    fun observeCard(cardId: String): Flow<LocalCard>

    @Query("SELECT * from cards WHERE deckId = :deckId")
    fun observeCardsByDeck(deckId: String): Flow<List<LocalCard>>

}