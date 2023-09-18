package com.example.learn.data.local

import com.example.learn.data.DecksRepository
import kotlinx.coroutines.flow.Flow

class OfflineDecksRepository(
    private val decksDao: DecksDao,
    private val deckCardCrossRefDao: DeckCardCrossRefDao,
): DecksRepository {

    override suspend fun createDeck(deck: LocalDeck): LocalDeck {
        decksDao.insert(deck)
        return deck
    }
    override suspend fun deleteDeck(deckId: String) {
        deckCardCrossRefDao.deleteAll(deckId)
        decksDao.getDeck(deckId)?.let { decksDao.delete(it) }
    }
    override suspend fun getDeck(deckId: String): LocalDeck {
        return decksDao.getDeck(deckId) ?: throw Exception("Requested deck ($deckId) does not exist in the data base")
    }
    override fun getDeckStream(deckId: String): Flow<LocalDeck> = decksDao.observeDeck(deckId)

    override fun getDecksStream(): Flow<List<LocalDeck>> = decksDao.observeDecks()

    override suspend fun updateDeck(deck: LocalDeck) {
        decksDao.insert(deck)
    }

    override suspend fun addDeckCardCrossRef(deckId: String, cardId: String) {
        deckCardCrossRefDao.insertCrossRef(DeckCardCrossRef(deckId, cardId))
    }

    override suspend fun removeDeckCardCrossRef(deckId: String, cardId: String) {
        deckCardCrossRefDao.deleteCrossRef(DeckCardCrossRef(deckId, cardId))
    }

    override fun getCardIdsStream(deckId: String): Flow<List<String>> = decksDao.getCardIdsStream(deckId)

}