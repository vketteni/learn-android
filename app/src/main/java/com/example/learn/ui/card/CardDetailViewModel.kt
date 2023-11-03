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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CardDetailViewModel(
    private val decksRepository: DecksRepository,
    private val cardsRepository: CardsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val cardId: String = checkNotNull(savedStateHandle[LearnDestinationArguments.CARD_ID_ARG])
    val deckId: String = checkNotNull(savedStateHandle[LearnDestinationArguments.DECK_ID_ARG])
    private val _isDeleted = MutableStateFlow(false)
    private val _displayFront = MutableStateFlow(true)
    private val _card = cardsRepository.getCardStream(cardId)
    private val _cardIds = decksRepository.getCardIdsStream(deckId)
    var uiState: StateFlow<CardUiState> = combine(
        _displayFront, _card, _cardIds, _isDeleted
    ) { displayFront, card, cardIds, isCardDeleted ->
        if (card == null) {
            CardUiState(isDeleted = true)
        } else {
            CardUiState(
                contentFront = card.content.front,
                contentBack = card.content.back,
                cardPosition = card.reference.position,
                deckLength = cardIds.size,
                created = card.created,
                actionEnabled = true,
                displayFront = displayFront,
                isDeleted = isCardDeleted
            )
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = CardUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun deleteCard() {
        viewModelScope.launch {
            decksRepository.removeDeckCardCrossRef(deckId, cardId)
            cardsRepository.deleteCard(cardId)
            _isDeleted.value = true
        }
    }
    suspend fun getCardIdByCardPosition(position: Int): String = decksRepository.getCardIdsStream(deckId).first()[position]

    suspend fun getAdjacentCardId(forward: Boolean): String {
        val cardIds = decksRepository.getCardIdsStream(deckId).first()
        val currentCard = uiState.value
        val newPosition = if (forward) {
            if (currentCard.cardPosition >= currentCard.deckLength - 1) 0
            else currentCard.cardPosition + 1
        } else {
            if (currentCard.cardPosition == 0) currentCard.deckLength - 1
            else currentCard.cardPosition - 1
        }
        return cardIds[newPosition]
    }

    fun switchSides() {
        _displayFront.value = !_displayFront.value
    }

}