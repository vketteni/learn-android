package com.example.learn.data

import com.example.learn.data.local.LocalDeck
import kotlinx.coroutines.flow.Flow

interface DecksRepository {

    /**
     * Retrieves a stream of all card decks.
     *
     * @return A Flow representing a list of card decks.
     */
    fun getDecksStream(): Flow<List<LocalDeck>>

    /**
     * Refreshes all card decks.
     */
    suspend fun refreshDecks()

    /**
     * Retrieves a specific card deck.
     *
     * @return A Flow representing a single card deck.
     */
    fun getDeckStream(deckId: String): Flow<LocalDeck>

    /**
     * Refreshes a card deck identified by the specified deck ID.
     *
     * @param deckId The ID of the deck to refresh.
     */
    suspend fun refreshDeck(deckId: String)

    /**
     * Creates a new card deck with the given title.
     *
     * @param title The title of the new deck.
     * @return The created deck object.
     */
    suspend fun createDeck(title: String): LocalDeck

    /**
     * Updates the title of a card deck identified by the specified deck ID.
     *
     * @param deckId The ID of the deck to update.
     * @param title The new title for the deck.
     */
    suspend fun updateDeck(deckId: String, title: String)

    /**
     * Deletes all card decks.
     */
    suspend fun deleteAllDecks()

    /**
     * Deletes a card deck identified by the specified deck ID.
     *
     * @param deckId The ID of the deck to delete.
     */
    suspend fun deleteDeck(deckId: String)

}