package com.example.learn.ui.deck

import com.example.learn.data.source.local.LocalDeck

data class DeckUiState(
    val title: String = "",
    val created: Long = 0,
    val actionEnabled: Boolean = false
)

fun LocalDeck.toDeckUiState(actionEnabled: Boolean = false): DeckUiState = DeckUiState(
    title = title,
    created = created,
    actionEnabled = actionEnabled
)

fun DeckUiState.toDeck(): LocalDeck = LocalDeck(
    title = title,
    created = created
)

fun DeckUiState.isValid(): Boolean {
    return title.isNotBlank()
}