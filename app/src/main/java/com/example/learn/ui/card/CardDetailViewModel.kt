package com.example.learn.ui.card

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn.data.CardsRepository
import com.example.learn.ui.navigation.LearnDestinationArguments
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class CardDetailViewModel(
    cardsRepository: CardsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val cardId: String = checkNotNull(savedStateHandle[LearnDestinationArguments.CARD_ID_ARG])

    private val _showFront = MutableStateFlow(true)
    private val _card = cardsRepository.getCardStream(cardId)
    val uiState: StateFlow<CardUiState> = combine(
        _showFront, _card
    ) { showFront, card ->
        CardUiState(
            id = card.id,
            deckId = card.deckId,
            contentFront = card.frontContent,
            contentBack = card.backContent,
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

    fun switchSides() {
        _showFront.value = !_showFront.value
    }

}