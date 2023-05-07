package com.example.learn.ui.deck

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.learn.data.Card

// Deck composable
@Composable
fun DeckDetailScreen(
    onNavigateCard: (card: Card) -> Unit,
    onCreateCard: () -> Unit,
    onDeleteDeck: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Cards list composable
    Column() {

    }
}
