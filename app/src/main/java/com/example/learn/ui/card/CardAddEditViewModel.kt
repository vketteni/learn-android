package com.example.learn.ui.card

import CardContent
import CardReference
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn.data.CardsRepository
import com.example.learn.data.DecksRepository
import com.example.learn.ui.navigation.LearnDestinationArguments
import kotlinx.coroutines.launch

class CardAddEditViewModel(
    private val cardsRepository: CardsRepository,
    private val decksRepository: DecksRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val cardId: String? = savedStateHandle[LearnDestinationArguments.CARD_ID_ARG]
    val deckId: String = checkNotNull(savedStateHandle[LearnDestinationArguments.DECK_ID_ARG])
    var cardUiState by mutableStateOf(CardUiState())
        private set

    init {
        if (cardId != null) {
            loadCard(cardId)
        }
    }

    fun updateUiState(newCardUiState: CardUiState) {
        cardUiState = newCardUiState.copy(
            actionEnabled = newCardUiState.isValid()
        )
    }

    fun saveCard() {
        if (cardId == null) {
            createCard()
        } else {
            updateCard()
        }
    }

    private fun createCard() {
        viewModelScope.launch {
            val card = cardsRepository.createCard(
                CardContent(cardUiState.contentFront, cardUiState.contentBack),
                deckId
            )
            decksRepository.addDeckCardCrossRef(deckId, card.cardId)
        }
    }

    private fun updateCard() {
        if (cardId == null) {
            throw RuntimeException("updateCard() was called but card is new.")
        }
        viewModelScope.launch {
            val card = cardsRepository.getCard(cardId)
            cardsRepository.updateCard(
                card.copy(
                    content = CardContent(cardUiState.contentFront, cardUiState.contentBack),
                    reference = CardReference(cardUiState.cardPosition, cardUiState.cardTitle)
                )
            )
        }
    }

    private fun loadCard(cardId: String) {
        cardUiState = cardUiState.copy(
            isLoading = true
        )
        viewModelScope.launch {
            cardsRepository.getCard(cardId).let { card ->
                cardUiState = cardUiState.copy(
                        created = card.created,
                        contentFront = card.content.front,
                        contentBack = card.content.back,
                        cardPosition = card.reference.position,
                        cardTitle = card.reference.title,
                        isLoading = false,
                    )
            }
        }
    }
}