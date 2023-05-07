package com.example.learn.ui.deck

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.learn.data.Deck

// Deck composable
@Composable
fun DeckOverviewScreen(
    onNavigateDeck: (deck: Deck) -> Unit,
    onCreateDeck: () -> Unit,
    onUpdateDeck: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Cards list composable
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column() {
            
        }
    }
}
