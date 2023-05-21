package com.example.learn.ui.deck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.learn.data.DecksRepository

class DeckEntryViewModel(private val decksRepository: DecksRepository): ViewModel() {

    var deckUiState by mutableStateOf(DeckUiState())
    private set

    fun updateUiState(newUiState: DeckUiState) {
        deckUiState = newUiState.copy()
    }

    suspend fun saveDeck() {
        decksRepository.createDeck(deckUiState.title)
    }
}