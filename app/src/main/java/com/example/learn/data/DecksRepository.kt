package com.example.learn.data

import com.example.learn.data.local.LocalDeck
import kotlinx.coroutines.flow.Flow

interface DecksRepository {

    fun getDecksStream(): Flow<List<LocalDeck>>

    fun getDeckStream(deckId: String): Flow<LocalDeck>

    suspend fun getDeck(deckId: String): LocalDeck

    suspend fun createDeck(title: String): LocalDeck

    suspend fun updateDeck(deck: LocalDeck)

}