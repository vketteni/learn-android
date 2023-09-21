package com.example.learn.data

import Card
import CardContent
import CardReference
import kotlinx.coroutines.flow.Flow

interface CardsRepository {

    fun getCardStream(cardId: String): Flow<Card?>
    suspend fun getCard(cardId: String): Card
    suspend fun updateCard(card: Card)
    suspend fun deleteCard(cardId: String)
    suspend fun createCard(content: CardContent, deckId: String): Card
    suspend fun getCardReference(cardId: String): CardReference
}