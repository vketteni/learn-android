package com.example.learn.data

import com.example.learn.data.local.LocalCard
import kotlinx.coroutines.flow.Flow

interface CardsRepository {

    fun getCardsByDeckStream(deckId: String): Flow<List<LocalCard>>

    suspend fun getCard(cardId: String): LocalCard?

    suspend fun updateCard(card: LocalCard)

    suspend fun createCard(front: String, back: String, deckId: String)
}