package com.example.learn.ui.card

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn.data.CardsRepository
import com.example.learn.data.DecksRepository
import com.example.learn.data.local.LocalDeck
import com.example.learn.ui.navigation.LearnDestinationArguments
import kotlinx.coroutines.launch

class CardAddEditViewModel(
    private val cardsRepository: CardsRepository,
    private val decksRepository: DecksRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val cardId: String? = savedStateHandle[LearnDestinationArguments.CARD_ID_ARG]
    private val deckId: String = savedStateHandle[LearnDestinationArguments.DECK_ID_ARG]!!

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
            val deck = decksRepository.getDeck(deckId)
            val card = cardsRepository.createCard(
                deckId = deckId,
                contentFront = cardUiState.contentFront,
                contentBack = cardUiState.contentBack,
            )

            val cardIds: MutableList<String> = deck.cardReferences.toMutableList()
            cardIds.add(card.id)
            decksRepository.updateDeck(
                LocalDeck(
                    id = deck.id,
                    title = deck.title,
                    cardReferences = cardIds,
                )
            )
        }
    }

    private fun updateCard() {
        if (cardId == null) {
            throw RuntimeException("updateCard() was called but card is new.")
        }
        viewModelScope.launch {
            cardsRepository.updateCard(
                cardUiState.toLocalCard()
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
                        id = card.id,
                        deckId = card.deckId,
                        created = card.created,
                        contentFront = card.frontContent,
                        contentBack = card.backContent,
                        isLoading = false,
                    )
            }
        }
    }
}