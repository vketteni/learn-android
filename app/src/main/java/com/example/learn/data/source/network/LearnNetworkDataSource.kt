package com.example.learn.data.source.network

import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class LearnNetworkDataSource: NetworkDataSource {
    private val accessMutex = Mutex()
    private var networkDecks: List<NetworkDeck> = listOf(
        NetworkDeck(
            deckId = "1",
            title = "English",
            created = 1,
        ),

        NetworkDeck(
            deckId = "2",
            title = "French",
            created = 2,
        ),

        NetworkDeck(
            deckId = "3",
            title = "Spanish",
            created = 3,
        ),
    )
    private var networkCards: List<NetworkCard> = listOf(
        NetworkCard(
            cardId = "1",
            frontContent = "Hello",
            backContent = "Hallo",
            title = "Hello",
            position = 1,
            created = 1,
        ),
        NetworkCard(
            cardId = "2",
            frontContent = "Dog",
            backContent = "Hund",
            title = "Dog",
            position = 2,
            created = 1,
        ),
        NetworkCard(
            cardId = "3",
            frontContent = "Cat",
            backContent = "Katze",
            title = "Cat",
            position = 3,
            created = 1,
        ),
        NetworkCard(
            cardId = "4",
            frontContent = "Bonjour",
            backContent = "Hallo",
            title = "Bonjour",
            position = 1,
            created = 1,
        ),
        NetworkCard(
            cardId = "5",
            frontContent = "Le chat",
            backContent = "Katze",
            title = "Let chat",
            position = 2,
            created = 1,
        ),
        NetworkCard(
            cardId = "6",
            frontContent = "Le chien",
            backContent = "Hund",
            title = "Le chien",
            position = 3,
            created = 1,
        ),
        NetworkCard(
            cardId = "7",
            frontContent = "Hola",
            backContent = "Hallo",
            title = "Hola",
            position = 1,
            created = 1,
        ),
        NetworkCard(
            cardId = "8",
            frontContent = "El gato",
            backContent = "Katze",
            title = "El gato",
            position = 2,
            created = 1,
        ),
        NetworkCard(
            cardId = "9",
            frontContent = "El perro",
            backContent = "Hund",
            title = "El perro",
            position = 3,
            created = 1,
        ),
    )
    private var networkDeckCardCrossRefs = listOf(
        NetworkDeckCardCrossRef(deckId = "1", cardId = "1"),
        NetworkDeckCardCrossRef(deckId = "1", cardId = "2"),
        NetworkDeckCardCrossRef(deckId = "1", cardId = "3"),
        NetworkDeckCardCrossRef(deckId = "2", cardId = "4"),
        NetworkDeckCardCrossRef(deckId = "2", cardId = "5"),
        NetworkDeckCardCrossRef(deckId = "2", cardId = "6"),
        NetworkDeckCardCrossRef(deckId = "3", cardId = "7"),
        NetworkDeckCardCrossRef(deckId = "3", cardId = "8"),
        NetworkDeckCardCrossRef(deckId = "3", cardId = "9"),
    )
    override suspend fun saveCards(cards: List<NetworkCard>) {
        networkCards = cards
    }
    override suspend fun saveDecks(decks: List<NetworkDeck>) {
        networkDecks = decks
    }
    override suspend fun loadCards(): List<NetworkCard> {
        accessMutex.withLock {
            delay(SERVICE_LATENCY_IN_MILLIS)
            return networkCards
        }

    }
    override suspend fun loadDecks(): List<NetworkDeck> {
        accessMutex.withLock {
            delay(SERVICE_LATENCY_IN_MILLIS)
            return networkDecks
        }
    }

    override suspend fun saveDeckCardCrossRefs(deckCardCrossRefs: List<NetworkDeckCardCrossRef>) {
        networkDeckCardCrossRefs = deckCardCrossRefs
    }

    override suspend fun loadDeckCardCrossRefs(): List<NetworkDeckCardCrossRef> {
        accessMutex.withLock {
            delay(SERVICE_LATENCY_IN_MILLIS)
            return networkDeckCardCrossRefs
        }
    }

}

private const val SERVICE_LATENCY_IN_MILLIS = 2000L