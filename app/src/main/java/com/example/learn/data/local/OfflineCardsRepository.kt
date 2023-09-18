package com.example.learn.data.local

import com.example.learn.data.CardsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class OfflineCardsRepository(
    private val cardsDao: CardsDao,
    private val decksDao: DecksDao,
    private val deckCardCrossRefDao: DeckCardCrossRefDao
): CardsRepository {
    override suspend fun getCard(cardId: String): LocalCard {
        return withContext(Dispatchers.IO) {
            cardsDao.getCard(cardId) ?: throw Exception("Card with specified id doesn't exist.")
        }
    }
    override suspend fun deleteCard(cardId: String) {
        val card = cardsDao.getCard(cardId) ?: throw Exception("Card with specified id doesn't exist.")
        cardsDao.delete(card)
    }
    override fun getCardStream(cardId: String): Flow<LocalCard?> = cardsDao.observeCard(cardId)
    override suspend fun updateCard(card: LocalCard) {
        val reference = card.reference.copy(title = takeTitle(card.content.front))
        cardsDao.update(card.copy(reference = reference))
    }
    override suspend fun createCard(content: CardContent, deckId: String): LocalCard {
            val position: Int = decksDao.getCardIdsStream(deckId).first().size
            val title: String = takeTitle(content.front)
            val card = LocalCard(
                content = CardContent(content.front, content.back),
                reference = CardReference(position, title)
            )
            cardsDao.insert(card)
            return card

    }
    override suspend fun getCardReference(cardId: String): CardReference {
        return withContext(Dispatchers.IO) {
            cardsDao.getCardReference(cardId) ?: throw Exception("Card with specified id doesn't exist.")
        }
    }
}

private fun takeTitle(content: String): String {
    val titleLength = 15
    return if (content.length > titleLength) content.substring(0, titleLength) else content
}