package com.example.learn.ui.card

data class CardUiState(
    val contentFront: String = "",
    val contentBack: String = "",
    val cardTitle: String = "",
    val cardPosition: Int = 0,
    val deckLength: Int = 0,
    val created: Long = 0,
    val actionEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val isDeleted: Boolean = false,
    val displayFront: Boolean = true,
)

data class CardUiReference(
    val cardId: String,
    val title: String,
    val position: Int,
)

data class CardUiContent(
    val front: String,
    val back: String,
)

fun CardUiState.isValid(): Boolean {
    return contentFront.isNotBlank() && contentBack.isNotBlank()
}