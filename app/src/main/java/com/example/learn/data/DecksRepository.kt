package com.example.learn.data

import Deck
import kotlinx.coroutines.flow.Flow

interface DecksRepository {

    fun getDecksStream(): Flow<List<Deck>>

    fun getDeckStream(deckId: String): Flow<Deck>

    suspend fun getDeck(deckId: String): Deck

    suspend fun createDeck(title: String): String

    suspend fun deleteDeck(deckId: String)
    suspend fun updateDeck(deck: Deck)

    suspend fun addDeckCardCrossRef(deckId: String, cardId: String)
    suspend fun removeDeckCardCrossRef(deckId: String, cardId: String)
    fun getCardIdsStream(deckId: String): Flow<List<String>>
}