package com.example.learn.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CardsDao {

    @Query("SELECT * from cards WHERE cardId = :cardId")
    fun observeCard(cardId: String): Flow<LocalCard?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(localCard: LocalCard)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(localCard: LocalCard)

    @Query("SELECT * from cards WHERE cardId = :cardId")
    suspend fun getCard(cardId: String) : LocalCard?
    @Query("DELETE FROM cards WHERE cardId = :cardId")
    suspend fun deleteById(cardId: String)
    @Query("SELECT position from cards WHERE cardId = :cardId")
    suspend fun getCardPosition(cardId: String): Int?
    @Query("SELECT title from cards WHERE cardId = :cardId")
    suspend fun getCardTitle(cardId: String): String?
    @Query("SELECT * FROM cards")
    suspend fun getAll(): List<LocalCard>
}