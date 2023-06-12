package com.example.learn.data

import com.example.learn.data.local.DecksDao
import com.example.learn.data.local.LocalDeck
import kotlinx.coroutines.flow.Flow

class OfflineDecksRepository(
    private val decksDao: DecksDao
): DecksRepository {

    override suspend fun createDeck(title: String): LocalDeck {
        val deck = LocalDeck(title=title)
        decksDao.insert(deck)
        return deck
    }

    override suspend fun deleteAllDecks() = decksDao.deleteDecks()

    override fun getDeckStream(deckId: String): Flow<LocalDeck> = decksDao.observeDeck(deckId)

    override fun getDecksStream(): Flow<List<LocalDeck>> = decksDao.observeDecks()

    override suspend fun deleteDeck(deckId: String) = decksDao.deleteDeck(deckId)

    override suspend fun refreshDecks() {
        TODO("Not yet implemented")
    }

    override suspend fun refreshDeck(deckId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateDeck(deckId: String, title: String) {
        val deck = decksDao.getDeck(deckId)?.copy(
            title = title,
        ) ?: throw Exception("Deck (id $deckId) not found")
        decksDao.insert(deck)
    }
}