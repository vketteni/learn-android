package com.example.learn.data

import com.example.learn.data.local.LocalCard
import kotlinx.coroutines.flow.Flow

interface CardsRepository {

    fun getCardStream(cardId: String): Flow<LocalCard>
    suspend fun getCard(cardId: String): LocalCard
    suspend fun updateCard(card: LocalCard)
    suspend fun createCard(deckId: String, contentFront: String, contentBack: String): LocalCard
    suspend fun getCardTitle(cardId: String): String

}