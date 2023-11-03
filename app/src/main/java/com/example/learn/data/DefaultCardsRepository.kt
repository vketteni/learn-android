package com.example.learn.data

import Card
import CardContent
import CardReference
import com.example.learn.data.source.local.CardsDao
import com.example.learn.data.source.local.DeckCardCrossRefDao
import com.example.learn.data.source.local.DecksDao
import com.example.learn.data.source.local.LocalCard
import com.example.learn.data.source.network.NetworkDataSource
import com.example.learn.data.source.toExternal
import com.example.learn.data.source.toLocal
import com.example.learn.data.source.toNetworkCards
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DefaultCardsRepository(
    private val cardsDao: CardsDao,
    private val deckCardCrossRefDao: DeckCardCrossRefDao,
    private val networkDataSource: NetworkDataSource,
    private val dispatcher: CoroutineDispatcher,
    private val scope: CoroutineScope
): CardsRepository {
    override suspend fun getCard(cardId: String): Card {
        return withContext(Dispatchers.IO) {
            cardsDao.getCard(cardId)?.toExternal() ?: throw Exception("Card with specified id doesn't exist.")
        }
    }
    override suspend fun deleteCard(cardId: String) {
        cardsDao.deleteById(cardId)
        saveCardsToNetwork()
    }
    override fun getCardStream(cardId: String): Flow<Card?> = cardsDao.observeCard(cardId).map { it?.toExternal() }
    override suspend fun updateCard(card: Card) {
        cardsDao.update(card.toLocal())
        saveCardsToNetwork()
    }
    override suspend fun createCard(content: CardContent, deckId: String): Card {
        val position: Int = deckCardCrossRefDao.getReferencedCardIdsStream(deckId).first().size
        val title: String = takeTitle(content.front)
        val card = LocalCard(
            frontContent = content.front,
            backContent = content.back,
            position = position,
            title = title,
        )
        cardsDao.insert(card)
        saveCardsToNetwork()
        return card.toExternal()

    }
    override suspend fun getCardReference(cardId: String): CardReference {
        return withContext(Dispatchers.IO) {
            val position = cardsDao.getCardPosition(cardId)
                ?: throw Exception("Card with specified id doesn't exist.")
            val title = cardsDao.getCardTitle(cardId)
                ?: throw Exception("Card with specified id doesn't exist.")
            CardReference(position, title)
        }
    }
    private fun saveCardsToNetwork() {
        scope.launch {
            try {
                val localCards = cardsDao.getAll()
                val networkCards = withContext(dispatcher) {
                    localCards.toNetworkCards()
                }
                networkDataSource.saveCards(networkCards)
            } catch (e: Exception) {
                // handle exception by exposing flow to top level ui state holder to display toast message
            }
        }
    }
}

private fun takeTitle(content: String): String {
    val titleLength = 15
    return if (content.length > titleLength) content.substring(0, titleLength) else content
}