package com.example.learn.ui.deck

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learn.LearnTopBar
import com.example.learn.R
import com.example.learn.data.local.LocalDeck
import com.example.learn.ui.AppViewModelProvider
import kotlinx.coroutines.flow.StateFlow

// Deck composable
@Composable
fun DeckOverviewScreen(
    onNavigateDeck: (localDeck: LocalDeck) -> Unit,
    onNavigateDeckEntry: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DeckOverviewViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Scaffold(
        topBar = {
            LearnTopBar(title = stringResource(R.string.decks_overview_title), canNavigateBack = false) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateDeckEntry() },
                modifier = Modifier
                    .navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surfaceTint
                )
            }
        },
    ) { innerPadding ->
        // Collecting Ui State and preserve the value via remember()
        val deckOverviewUiStateFlow: StateFlow<DeckOverviewUiState> =
            viewModel.deckOverviewUiState

        val deckOverviewUiState: State<DeckOverviewUiState> =
            deckOverviewUiStateFlow.collectAsState()

        val currentState: DeckOverviewUiState =
            remember(deckOverviewUiState.value) {
            deckOverviewUiState.value
        }
        // Alternatively: val currentState by viewModel.deckOverviewUiState.collectAsState()

        DeckOverviewBody(
            localDeckList = currentState.deckList,
            onNavigateDeck = onNavigateDeck,
            modifier = modifier
                .padding(innerPadding)
        )
    }
}

@Composable
fun DeckOverviewBody(
    localDeckList: List<LocalDeck>,
    onNavigateDeck: (localDeck: LocalDeck) -> Unit,
    modifier: Modifier = Modifier
) {
    if (localDeckList.isEmpty()) {
        Text(
            text = "No decks existent, create a deck?",
            style = MaterialTheme.typography.titleMedium
        )
    } else {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            items(items = localDeckList, key = { it.id }) { item ->
                DeckCard(localDeck = item, onNavigateDeck = onNavigateDeck)
                Divider()
            }
        }
    }
}

@Composable
fun DeckCard(
    localDeck: LocalDeck,
    onNavigateDeck: (localDeck: LocalDeck) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable { onNavigateDeck(localDeck) }
        .padding(vertical = 16.dp)
    ) {
         Text(
             text = localDeck.title,
             modifier = Modifier.weight(1.5f),
             fontWeight = FontWeight.Bold
         )
    }
}