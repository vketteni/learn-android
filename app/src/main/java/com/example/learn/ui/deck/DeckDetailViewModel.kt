package com.example.learn.ui.deck

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn.data.CardsRepository
import com.example.learn.data.DecksRepository
import com.example.learn.ui.card.CardUiReference
import com.example.learn.ui.navigation.LearnDestinationArguments
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


data class DeckDetailUiState(
    val cardReferences: List<CardUiReference> = listOf(),
    var selectedCard: CardUiReference? = null,
    val loading: Boolean = false,
)

class DeckDetailViewModel(
    private val cardsRepository: CardsRepository,
    private val decksRepository: DecksRepository,
    savedStateHandle: SavedStateHandle
    ): ViewModel() {

    val deckId: String = checkNotNull(savedStateHandle[LearnDestinationArguments.DECK_ID_ARG])

    private val _uiState: MutableStateFlow<DeckDetailUiState> = MutableStateFlow(DeckDetailUiState())
    val uiState: StateFlow<DeckDetailUiState> = _uiState

    init {
        viewModelScope.launch {
            decksRepository.getCardIdsStream(deckId)
                .map { cardIds ->
                    cardIds.map { cardId ->
                        val cardReference = cardsRepository.getCardReference(cardId)
                        CardUiReference(cardId, cardReference.title, cardReference.position)
                    }
                }
                .collect { cardReferences ->
                    val currentState = _uiState.value
                    val updatedState = currentState.copy(cardReferences = cardReferences)
                    _uiState.value = updatedState
                }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun onCardSelect(card: CardUiReference) {
        viewModelScope.launch {
            val currentState = _uiState.value
            val updatedState = currentState.copy(selectedCard = card)
            _uiState.value = updatedState
        }
    }
    fun deleteDeck() {
        viewModelScope.launch {
            decksRepository.deleteDeck(deckId)
        }
    }
    fun onCardPositionChanged(card: CardUiReference, newPosition: Int) {
        val currentState = _uiState.value
        val updatedList = currentState.cardReferences.toMutableList()
        updatedList.remove(card)
        updatedList.add(newPosition, card)

        val updatedState = currentState.copy(cardReferences = updatedList)
        _uiState.value = updatedState
    }

}
