package com.example.learn.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CardsDao {

    @Query("SELECT * from cards WHERE cardId = :cardId")
    fun observeCard(cardId: String): Flow<LocalCard>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(localCard: LocalCard)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(localCard: LocalCard)

    @Query("SELECT * from cards WHERE cardId = :cardId")
    suspend fun getCard(cardId: String) : LocalCard?
    @Delete
    suspend fun delete(localCard: LocalCard)
    @Query("SELECT reference from cards WHERE cardId = :cardId")
    suspend fun getCardReference(cardId: String): CardReference?
}