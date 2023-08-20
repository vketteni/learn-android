package com.example.learn.ui.card

import com.example.learn.data.local.LocalCard

data class CardUiState(
    val id: String = "",
    val deckId: String = "",
    val contentFront: String = "",
    val contentBack: String = "",
    val created: Long = 0,
    val actionEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val isFront: Boolean = true,
)

fun LocalCard.toCardUiState(actionEnabled: Boolean = false): CardUiState {
    return CardUiState(
        id = id,
        deckId = deckId,
        contentFront = frontContent,
        contentBack = backContent,
        created = created,
        actionEnabled = actionEnabled,
        isLoading = false,
        isSaved = false,
    )
}

fun CardUiState.toLocalCard(): LocalCard {
    return LocalCard(
        id=id,
        deckId = deckId,
        frontContent=contentFront,
        backContent=contentBack,
        created = created,
    )
}

fun CardUiState.isValid(): Boolean {
    return contentFront.isNotBlank() && contentBack.isNotBlank()
}