package com.example.learn.ui.deck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.learn.data.DecksRepository

/**
 * View Model to
 */
class DeckOverviewViewModel(private val decksRepository: DecksRepository): ViewModel() {
    /**
     * Holds current deck ui state
     */
    var deckUiState by mutableStateOf(DeckUiState())
        private set

    /**
     *
     */
}