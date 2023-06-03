package com.example.learn.ui.card

import com.example.learn.data.local.LocalCard

data class CardUiState(
    val id: String = "",
    val deckId: String = "",
    val frontContent: String = "",
    val backContent: String = "",
    val created: Long = 0,
    val actionEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isCardSaved: Boolean = false,
)

fun LocalCard.toCardUiState(actionEnabled: Boolean = false): CardUiState {
    return CardUiState(
        id = id,
        deckId = deckId,
        frontContent = front,
        backContent = back,
        created = created,
        actionEnabled = actionEnabled,
        isLoading = false,
        isCardSaved = false
    )
}

fun CardUiState.toLocalCard(): LocalCard {
    return LocalCard(
        id=id,
        deckId = deckId,
        front=frontContent,
        back=backContent,
        created = created
    )
}

fun CardUiState.isValid(): Boolean {
    return frontContent.isNotBlank() && backContent.isNotBlank()
}