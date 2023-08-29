package com.example.learn.ui.deck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn.data.DecksRepository
import com.example.learn.ui.navigation.LearnDestinationArguments
import kotlinx.coroutines.launch

class DeckAddEditViewModel(
    private val decksRepository: DecksRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val deckId: String? = savedStateHandle[LearnDestinationArguments.DECK_ID_ARG]
    init {
        if (deckId != null)
            loadDeck(deckId)
    }
    var deckUiState by mutableStateOf(DeckUiState())
    private set

    fun updateUiState(newUiState: DeckUiState) {
        val actionEnabled = newUiState.isValid()
        deckUiState = newUiState.copy(
            actionEnabled = actionEnabled
        )

    }
    private fun loadDeck(deckId: String) {
        viewModelScope.launch {
            decksRepository.getDeck(deckId).let { deck ->
                deckUiState = deckUiState.copy(
                    title = deck.title,
                )
            }
        }
    }
    fun saveDeck() {
        if (deckId.isNullOrEmpty()) {
            createDeck()
        } else {
            updateDeck()
        }
    }

    private fun createDeck() {
        viewModelScope.launch {
            decksRepository.createDeck(deckUiState.title)
        }
    }


    private fun updateDeck() {
        if (deckId != null) {
            viewModelScope.launch {
                val deck = decksRepository.getDeck(deckId)
                decksRepository.updateDeck(
                    deck.copy(
                        title = deckUiState.title
                    )
            )
        }
        }
    }
}