package com.example.learn.data

import com.example.learn.data.local.LocalDeck
import kotlinx.coroutines.flow.Flow

interface DecksRepository {

    fun getDecksStream(): Flow<List<LocalDeck>>

    fun getDeckStream(deckId: String): Flow<LocalDeck>

    suspend fun getDeck(deckId: String): LocalDeck

    suspend fun createDeck(deck: LocalDeck): LocalDeck

    suspend fun deleteDeck(deckId: String)
    suspend fun updateDeck(deck: LocalDeck)

    suspend fun addDeckCardCrossRef(deckId: String, cardId: String)
    suspend fun removeDeckCardCrossRef(deckId: String, cardId: String)
    fun getCardIdsStream(deckId: String): Flow<List<String>>
}