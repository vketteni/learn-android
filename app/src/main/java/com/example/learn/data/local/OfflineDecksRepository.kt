package com.example.learn.data.local

import com.example.learn.data.DecksRepository
import kotlinx.coroutines.flow.Flow

class OfflineDecksRepository(
    private val decksDao: DecksDao
): DecksRepository {

    override suspend fun createDeck(title: String): LocalDeck {
        val deck = LocalDeck(title=title)
        decksDao.insert(deck)
        return deck
    }

    override suspend fun getDeck(deckId: String): LocalDeck {
        return decksDao.getDeck(deckId) ?: throw Exception("Requested deck ($deckId) does not exist in the data base.")
    }

    override fun getDeckStream(deckId: String): Flow<LocalDeck> = decksDao.observeDeck(deckId)

    override fun getDecksStream(): Flow<List<LocalDeck>> = decksDao.observeDecks()

    override suspend fun updateDeck(deck: LocalDeck) {
        decksDao.insert(deck)
    }

}