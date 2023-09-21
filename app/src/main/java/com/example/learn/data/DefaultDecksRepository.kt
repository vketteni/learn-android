package com.example.learn.data

import Deck
import com.example.learn.data.source.local.DeckCardCrossRef
import com.example.learn.data.source.local.DeckCardCrossRefDao
import com.example.learn.data.source.local.DecksDao
import com.example.learn.data.source.network.NetworkDataSource
import com.example.learn.data.source.toExternal
import com.example.learn.data.source.toLocal
import com.example.learn.data.source.toNetworkDecks
import com.example.learn.data.source.toNetworkCrossRefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class DefaultDecksRepository(
    private val decksDao: DecksDao,
    private val deckCardCrossRefDao: DeckCardCrossRefDao,
    private val networkDataSource: NetworkDataSource,
    private val dispatcher: CoroutineDispatcher,
    private val scope: CoroutineScope
): DecksRepository {

    override suspend fun createDeck(title: String): String {
        val deckId = withContext(dispatcher) {
            UUID.randomUUID().toString()
        }
        val created = withContext(dispatcher) {
            System.currentTimeMillis()
        }
        val deck = Deck(deckId, created, title)
        decksDao.insert(deck.toLocal())
        saveDecksToNetwork()
        return deck.deckId
    }
    override suspend fun deleteDeck(deckId: String) {
        deckCardCrossRefDao.deleteAll(deckId)
        decksDao.deleteById(deckId)
        saveCrossRefsToNetwork()
        saveDecksToNetwork()
    }
    override suspend fun getDeck(deckId: String): Deck {
        return decksDao.getDeck(deckId)?.toExternal() ?: throw Exception("Requested deck ($deckId) does not exist in the data base")
    }
    override fun getDeckStream(deckId: String): Flow<Deck> = decksDao.observeDeck(deckId).map { it.toExternal() }

    override fun getDecksStream(): Flow<List<Deck>> = decksDao.observeDecks().map { it.map { deck -> deck.toExternal() } }

    override suspend fun updateDeck(deck: Deck) {
        decksDao.insert(deck.toLocal())
        saveDecksToNetwork()
    }

    override suspend fun addDeckCardCrossRef(deckId: String, cardId: String) {
        deckCardCrossRefDao.insertCrossRef(DeckCardCrossRef(deckId, cardId))
        saveCrossRefsToNetwork()
    }

    override suspend fun removeDeckCardCrossRef(deckId: String, cardId: String) {
        deckCardCrossRefDao.deleteCrossRef(DeckCardCrossRef(deckId, cardId))
        saveCrossRefsToNetwork()
    }

    override fun getCardIdsStream(deckId: String): Flow<List<String>> = decksDao.getCardIdsStream(deckId)
    private fun saveDecksToNetwork() {
        scope.launch {
            try {
                val localDecks = decksDao.getAll()
                val networkDecks = withContext(dispatcher) {
                    localDecks.toNetworkDecks()
                }
                networkDataSource.saveDecks(networkDecks)
            } catch (e: Exception) {
                // handle exception by exposing flow to top level ui state holder to display toast message
            }
        }
    }
    private fun saveCrossRefsToNetwork() {
        scope.launch {
            try {
                val localDeckCardCrossRefs = deckCardCrossRefDao.getAll()
                val networkDeckCardCrossRefs = withContext(dispatcher) {
                    localDeckCardCrossRefs.toNetworkCrossRefs()
                }
                networkDataSource.saveDeckCardCrossRefs(networkDeckCardCrossRefs)
            } catch (e: Exception) {
                // handle exception by exposing flow to top level ui state holder to display toast message
            }
        }
    }
}
