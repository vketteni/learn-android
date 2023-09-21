package com.example.learn.data.source.network


interface NetworkDataSource {
    suspend fun saveDecks(decks: List<NetworkDeck>)
    suspend fun loadDecks(): List<NetworkDeck>
    suspend fun saveCards(cards: List<NetworkCard>)
    suspend fun loadCards(): List<NetworkCard>
    suspend fun saveDeckCardCrossRefs(deckCardCrossRefs: List<NetworkDeckCardCrossRef>)
    suspend fun loadDeckCardCrossRefs(): List<NetworkDeckCardCrossRef>
}