package com.example.learn.ui.deck

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn.data.CardTitle
import com.example.learn.data.CardsRepository
import com.example.learn.ui.navigation.LearnDestinationArguments
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DeckDetailViewModel(
    private val cardsRepository: CardsRepository,
    savedStateHandle: SavedStateHandle
    ): ViewModel() {

    val deckId: String = checkNotNull(savedStateHandle[LearnDestinationArguments.DECK_ID_ARG])

    val deckDetailUiState: StateFlow<DeckDetailUiState> = cardsRepository.getCardTitlesStream(deckId).map { DeckDetailUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue =  DeckDetailUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class DeckDetailUiState(val cardTitleList: List<CardTitle> = listOf())