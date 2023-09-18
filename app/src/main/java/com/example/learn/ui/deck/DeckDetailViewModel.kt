package com.example.learn.ui.deck

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn.data.CardsRepository
import com.example.learn.data.DecksRepository
import com.example.learn.ui.card.CardUiReference
import com.example.learn.ui.navigation.LearnDestinationArguments
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class DeckDetailUiState(
    val cardReferences: List<CardUiReference> = listOf(),
    val loading: Boolean = false,
)

class DeckDetailViewModel(
    private val cardsRepository: CardsRepository,
    private val decksRepository: DecksRepository,
    savedStateHandle: SavedStateHandle
    ): ViewModel() {

    val deckId: String = checkNotNull(savedStateHandle[LearnDestinationArguments.DECK_ID_ARG])
    val uiState: StateFlow<DeckDetailUiState> = decksRepository.getCardIdsStream(deckId)
        .map { DeckDetailUiState(it.map { cardId ->
            val cardReference = cardsRepository.getCardReference(cardId)
            CardUiReference(cardId, cardReference.title, cardReference.position)})
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = DeckDetailUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun deleteDeck() {
        viewModelScope.launch {
            decksRepository.deleteDeck(deckId)
        }
    }
}