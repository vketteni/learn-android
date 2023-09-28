package com.example.learn.ui.deck

import Deck
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learn.DeckOverviewTopBar
import com.example.learn.ui.AppViewModelProvider
import kotlinx.coroutines.flow.StateFlow

// Deck composable
@Composable
fun DeckOverviewScreen(
    onNavigateDeckDetail: (deckId: String) -> Unit,
    onNavigateDeckAddEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DeckOverviewViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Scaffold(
        topBar = { DeckOverviewTopBar(onRefresh = { viewModel.refresh() })},
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateDeckAddEdit() },
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(16.dp) // Add padding to the FAB
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
    ) { innerPadding ->

        // Collecting Ui State from viewModel and preserve the value via remember()
        val deckOverviewUiStateFlow: StateFlow<DeckOverviewUiState> =
            viewModel.uiState

        val deckOverviewUiState: State<DeckOverviewUiState> =
            deckOverviewUiStateFlow.collectAsStateWithLifecycle()

        val currentState: DeckOverviewUiState =
            remember(deckOverviewUiState.value) {
                deckOverviewUiState.value
            }
        // Alternatively: val currentState by viewModel.deckOverviewUiState.collectAsState()

        DeckOverviewBody(
            deckList = currentState.decks,
            onNavigateDeckDetail = onNavigateDeckDetail,
            modifier = modifier
                .padding(innerPadding)
        )
    }
}

@Composable
fun DeckOverviewBody(
    deckList: List<Deck>,
    onNavigateDeckDetail: (deckId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (deckList.isEmpty()) {
        Text(
            text = "No decks existent, create a deck?",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp) // Add padding to the Text
        )
    } else {
        LazyColumn(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 8.dp) // Add padding to the LazyColumn
        ) {
            items(items = deckList, key = { it.deckId }) { item ->
                DeckCard(deck = item, onNavigateDeck = onNavigateDeckDetail)
                Divider(modifier = Modifier.padding(vertical = 8.dp)) // Add padding to the Divider
            }
        }
    }
}

@Composable
fun DeckCard(
    deck: Deck,
    onNavigateDeck: (deckId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNavigateDeck(deck.deckId) }
            .padding(vertical = 16.dp, horizontal = 16.dp) // Add padding to the Row
    ) {
        Text(
            text = deck.title,
            modifier = Modifier.weight(1.5f),
            fontWeight = FontWeight.Bold
        )
    }
}