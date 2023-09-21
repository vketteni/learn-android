package com.example.learn.ui.deck

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn.data.DecksRepository
import com.example.learn.ui.navigation.LearnDestinationArguments
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DeckAddEditUiState(
    val title: String = "",
    val actionEnabled: Boolean = false,
    val isSaved: Boolean = false,
)
class DeckAddEditViewModel(
    private val decksRepository: DecksRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val deckId: String? = savedStateHandle[LearnDestinationArguments.DECK_ID_ARG]
    init {
        if (deckId != null)
            loadDeck(deckId)
    }
    private val _uiState = MutableStateFlow(DeckAddEditUiState())
    val uiState = _uiState.asStateFlow()

    fun updateUiState(newUiState: DeckAddEditUiState) {
        val actionEnabled = newUiState.title.isNotBlank()
        _uiState.update {
            newUiState.copy(
                actionEnabled = actionEnabled
            )
        }

    }
    private fun loadDeck(deckId: String) {
        viewModelScope.launch {
            decksRepository.getDeck(deckId).let { deck ->
                _uiState.update {
                    it.copy(title = deck.title)
                }
            }
        }
    }
    fun saveDeck() {
        _uiState.update { it.copy(isSaved = true) }
        if (deckId.isNullOrEmpty()) {
            createDeck()
        } else {
            updateDeck()
        }
    }

    private fun createDeck() {
        viewModelScope.launch {
            decksRepository.createDeck(_uiState.value.title)
        }
    }


    private fun updateDeck() {
        if (deckId != null) {
            viewModelScope.launch {
                val deck = decksRepository.getDeck(deckId)
                decksRepository.updateDeck(
                    deck.copy(
                        title = _uiState.value.title
                    )
            )
        }
        }
    }
}