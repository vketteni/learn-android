package com.example.learn.ui.deck

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn.data.CardsRepository
import com.example.learn.data.DecksRepository
import com.example.learn.ui.navigation.LearnDestinationArguments
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class DeckDetailUiState(
    val cards: List<DeckDetailCardTitle> = listOf(),
    val loading: Boolean = false,
)

data class DeckDetailCardTitle(
    val cardId: String,
    val deckId: String,
    val title: String,
)

class DeckDetailViewModel(
    private val cardsRepository: CardsRepository,
    private val decksRepository: DecksRepository,
    savedStateHandle: SavedStateHandle
    ): ViewModel() {

    val deckId: String = checkNotNull(savedStateHandle[LearnDestinationArguments.DECK_ID_ARG])

    private val _uiState = MutableStateFlow(DeckDetailUiState(loading = true))

    val uiState: StateFlow<DeckDetailUiState> = _uiState.asStateFlow()


    init {
        refreshAll()
    }

    private fun refreshAll(){
        _uiState.update { it.copy(loading = true) }

        viewModelScope.launch {
            decksRepository.getDeckStream(deckId).collect { deck ->
                _uiState.update {
                    it.copy(
                        cards = deck.cardIds.map { cardId ->
                            DeckDetailCardTitle(
                                cardId,
                                deckId,
                                cardsRepository.getCardTitle(cardId),
                            )
                        },
                        loading = false
                    )
                }
            }
        }
    }
}