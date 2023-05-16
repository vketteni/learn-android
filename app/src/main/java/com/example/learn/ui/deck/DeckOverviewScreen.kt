package com.example.learn.ui.deck

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.learn.data.local.LocalDeck

// Deck composable
@Composable
fun DeckOverviewScreen(
    onNavigateDeck: (localDeck: LocalDeck) -> Unit,
    onCreateDeck: () -> Unit,
    onUpdateDeck: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { /* TODO */ },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
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
        DeckOverviewBody(
            localDeckList = listOf(
                LocalDeck("1","Spanisch-challange", 2000010),
                LocalDeck("2","Biologie-Neurology", 2000010),
                LocalDeck("3","Chess-Moves", 2000010),
            ),
            onNavigateDeck = {},
            onCreateDeck = { /*TODO*/ },
            onUpdateDeck = { /*TODO*/ },
            modifier = modifier
                .padding(innerPadding)
        )
    }
}

@Composable
fun DeckOverviewBody(
    localDeckList: List<LocalDeck>,
    onNavigateDeck: (localDeck: LocalDeck) -> Unit,
    onCreateDeck: () -> Unit,
    onUpdateDeck: () -> Unit,
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