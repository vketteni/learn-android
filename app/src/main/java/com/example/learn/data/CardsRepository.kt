package com.example.learn.data

import com.example.learn.data.local.CardContent
import com.example.learn.data.local.CardReference
import com.example.learn.data.local.LocalCard
import kotlinx.coroutines.flow.Flow

interface CardsRepository {

    fun getCardStream(cardId: String): Flow<LocalCard>
    suspend fun getCard(cardId: String): LocalCard
    suspend fun updateCard(card: LocalCard)
    suspend fun createCard(content: CardContent, deckId: String): LocalCard
    suspend fun getCardReference(cardId: String): CardReference
}