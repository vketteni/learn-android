package com.example.learn.data

import com.example.learn.data.local.LocalCard
import kotlinx.coroutines.flow.Flow

interface CardsRepository {

    fun getCardTitlesStream(deckId: String): Flow<List<CardTitle>>

    suspend fun getCard(cardId: String): LocalCard?

    suspend fun updateCard(card: LocalCard)

    suspend fun createCard(front: String, back: String, deckId: String)

    fun getCardStream(cardId: String): Flow<LocalCard>
}

data class CardTitle(val title: String, val cardId: String, val deckId: String)