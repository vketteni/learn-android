package com.example.learn.ui.card

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn.data.CardsRepository
import com.example.learn.ui.navigation.LearnDestinationArguments
import kotlinx.coroutines.launch

data class AddEditCardUiState(
    val front: String = "",
    val back: String = "",
    val isLoading: Boolean = false,
    val isCardSaved: Boolean = false,
)

class CardAddEditViewModel(
    private val cardsRepository: CardsRepository,
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
            createNewCard()
        } else {
            updateCard()
        }
    }

    private fun createNewCard() {
        viewModelScope.launch {
            cardsRepository.createCard(
                front = cardUiState.frontContent,
                back = cardUiState.backContent,
                deckId = deckId
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
                cardUiState = if (card != null) {
                    cardUiState.copy(
                        id = card.id,
                        deckId = card.deckId,
                        created = card.created,
                        frontContent = card.front,
                        backContent = card.back,
                        isLoading = false,
                    )
                } else {
                    cardUiState.copy(isLoading = false)

                }
            }
        }
    }




}