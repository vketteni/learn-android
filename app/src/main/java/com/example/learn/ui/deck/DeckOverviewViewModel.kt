package com.example.learn.ui.deck


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn.data.DecksRepository
import com.example.learn.data.local.LocalDeck
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * View Model to
 */
class DeckOverviewViewModel(decksRepository: DecksRepository): ViewModel() {
    /**
     * Holds current deck ui state
     */
    val deckOverviewUiState: StateFlow<DeckOverviewUiState> = decksRepository
        .getDecksStream().map { DeckOverviewUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = DeckOverviewUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }


}

/**
 * Ui state for DeckOverviewScreen
 */
data class DeckOverviewUiState(val deckList: List<LocalDeck> = listOf())