package com.example.learn.data

import com.example.learn.data.local.CardsDao
import com.example.learn.data.local.LocalCard
import kotlinx.coroutines.flow.Flow

class OfflineCardsRepository(
    private val cardsDao: CardsDao
): CardsRepository {

    override fun getCardsByDeckStream(deckId: String): Flow<List<LocalCard>> = cardsDao.observeCardsByDeck(deckId)

    override suspend fun getCard(cardId: String): LocalCard? = cardsDao.getCard(cardId)

    override suspend fun updateCard(card: LocalCard) = cardsDao.update(card)

    override suspend fun createCard(front: String, back: String, deckId: String) {
        val card = LocalCard(deckId = deckId, front = front, back = back)
        cardsDao.insert(card)
    }

}