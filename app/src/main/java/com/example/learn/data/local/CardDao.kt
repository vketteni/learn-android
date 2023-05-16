package com.example.learn.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(localCard: LocalCard)

    @Update
    suspend fun update(localCard: LocalCard)

    @Delete
    suspend fun delete(localCard: LocalCard)

    @Query("SELECT * from cards WHERE id = :id")
    fun observeCardById(id:String): Flow<LocalCard>

    @Query("SELECT * from cards ORDER BY created DESC")
    fun observeCards(): Flow<List<LocalCard>>
}