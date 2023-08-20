package com.example.learn.data.local

import com.example.learn.data.CardsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class OfflineCardsRepository(
    private val cardsDao: CardsDao
): CardsRepository {

    override suspend fun getCardTitle(cardId: String): String {
        return withContext(Dispatchers.IO) {
           cardsDao.getCardTitle(cardId) ?: throw Exception("Card with specified id doesn't exist.")
        }
    }

    override suspend fun getCard(cardId: String): LocalCard {
        return withContext(Dispatchers.IO) {
            cardsDao.getCard(cardId) ?: throw Exception("Card with specified id doesn't exist.")
        }
    }

    override fun getCardStream(cardId: String): Flow<LocalCard> = cardsDao.observeCard(cardId)

    override suspend fun updateCard(card: LocalCard) = cardsDao.update(card)

    override suspend fun createCard(deckId: String, contentFront: String, contentBack: String): LocalCard {
        val card = LocalCard(deckId = deckId, frontContent = contentFront, backContent = contentBack)
        cardsDao.insert(card)
        return card
    }

}