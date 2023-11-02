package com.example.learn.ui.card

import CardContent
import CardReference
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn.data.CardsRepository
import com.example.learn.data.DecksRepository
import com.example.learn.ui.navigation.LearnDestinationArguments
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardAddEditViewModel(
    private val cardsRepository: CardsRepository,
    private val decksRepository: DecksRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val cardId: String? = savedStateHandle[LearnDestinationArguments.CARD_ID_ARG]
    val deckId: String = checkNotNull(savedStateHandle[LearnDestinationArguments.DECK_ID_ARG])

    private var _uiState = MutableStateFlow(CardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        if (cardId != null) {
            loadCard(cardId)
        }
    }

    fun updateUiState(content: CardUiContent) {
        _uiState.update {
            val newCardUiState = _uiState.value.copy(
                contentFront = content.front,
                contentBack = content.back,
            )
            newCardUiState.copy(actionEnabled = newCardUiState.isValid())
        }
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
                CardContent(_uiState.value.contentFront, _uiState.value.contentBack),
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
                    content = CardContent(_uiState.value.contentFront, _uiState.value.contentBack),
                    reference = CardReference(_uiState.value.cardPosition, _uiState.value.cardTitle)
                )
            )
        }
    }

    private fun loadCard(cardId: String) {
        _uiState.update {
            _uiState.value.copy(isLoading = true)
        }

        viewModelScope.launch {
            cardsRepository.getCard(cardId).let { card ->
                _uiState.update {
                    _uiState.value.copy(
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
}