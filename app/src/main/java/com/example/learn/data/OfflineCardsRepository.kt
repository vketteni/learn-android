package com.example.learn.data

import com.example.learn.data.local.CardsDao
import com.example.learn.data.local.LocalCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class OfflineCardsRepository(
    private val cardsDao: CardsDao
): CardsRepository {

    override fun getCardTitlesStream(deckId: String): Flow<List<CardTitle>> = cardsDao.observeCardTitles(deckId)

    override suspend fun getCard(cardId: String): LocalCard? {
        return withContext(Dispatchers.IO) {
            cardsDao.getCard(cardId)
        }
    }

    override fun getCardStream(cardId: String): Flow<LocalCard> = cardsDao.observeCard(cardId)

    override suspend fun updateCard(card: LocalCard) = cardsDao.update(card)

    override suspend fun createCard(front: String, back: String, deckId: String) {
        val card = LocalCard(deckId = deckId, front = front, back = back)
        cardsDao.insert(card)
    }

}