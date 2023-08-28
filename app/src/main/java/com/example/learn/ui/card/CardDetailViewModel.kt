package com.example.learn.ui.card

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn.data.CardsRepository

import com.example.learn.data.DecksRepository
import com.example.learn.ui.navigation.LearnDestinationArguments
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class CardDetailViewModel(
    private val decksRepository: DecksRepository,
    cardsRepository: CardsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val cardId: String = checkNotNull(savedStateHandle[LearnDestinationArguments.CARD_ID_ARG])
    val deckId: String = checkNotNull(savedStateHandle[LearnDestinationArguments.DECK_ID_ARG])
    private val _showFront = MutableStateFlow(true)
    private val _card = cardsRepository.getCardStream(cardId)
    private val _deck = decksRepository.getDeckStream(deckId)
    val uiState: StateFlow<CardUiState> = combine(
        _showFront, _card, _deck
    ) { showFront, card, deck ->
        CardUiState(
            contentFront = card.content.front,
            contentBack = card.content.back,
            cardPosition = card.reference.position,
            deckLength = deck.cardIds.size,
            created = card.created,
            actionEnabled = true,
            isFront = showFront
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = CardUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun getIdByCardPosition(position: Int): String = decksRepository.getDeck(deckId).cardIds[position]

    fun switchSides() {
        _showFront.value = !_showFront.value
    }

}