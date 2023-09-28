package com.example.learn.ui.deck


import Deck
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn.data.DecksRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * View Model to
 */
class DeckOverviewViewModel(
    private val decksRepository: DecksRepository): ViewModel() {
    /**
     * Holds current deck ui state
     */
    val uiState: StateFlow<DeckOverviewUiState> = decksRepository
        .getDecksStream().map { DeckOverviewUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = DeckOverviewUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun refresh() {
        viewModelScope.launch {
            decksRepository.refresh()
        }
    }

}

/**
 * Ui state for DeckOverviewScreen
 */
data class DeckOverviewUiState(val decks: List<Deck> = listOf())