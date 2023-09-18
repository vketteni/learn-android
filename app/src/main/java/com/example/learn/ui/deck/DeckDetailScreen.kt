package com.example.learn.ui.deck

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learn.LearnTopBar
import com.example.learn.R
import com.example.learn.ui.AppViewModelProvider
import com.example.learn.ui.card.CardUiReference

// Deck composable
@Composable
fun DeckDetailScreen(
    onNavigateUp: () -> Unit,
    onNavigateCardDetail: (cardId: String) -> Unit,
    onNavigateAddCard: (deckId: String) -> Unit,
    onNavigateDeckEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DeckDetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                viewModel.deleteDeck()
                onDelete()
            },
            onDeleteCancel = { deleteConfirmationRequired = false }
        )
    }
    Scaffold(
        topBar = {
            LearnTopBar(
                title = R.string.deck_detail_screen_title,
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                actions = {
                    IconButton(onClick = onNavigateDeckEdit ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = { deleteConfirmationRequired = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateAddCard(viewModel.deckId)},
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
        }

    ) { innerPadding ->

        DeckDetailBody(
            modifier = modifier
                .padding(innerPadding),
            cards = uiState.cardReferences,
            onNavigateCardDetail = onNavigateCardDetail
        )
    }
}

@Composable
fun DeckDetailBody(
    cards: List<CardUiReference>,
    onNavigateCardDetail: (cardId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (cards.isEmpty()) {
        Text(
            text = "Your deck has no cards, create a card?",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp) // Add padding to the Text
        )
    } else {
        LazyColumn(modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp) // Add padding to the LazyColumn
        ) {
            items(items=cards, key = { it.cardId }) { card ->
                CardItem(card, onNavigateCardDetail)
                Divider(modifier = Modifier.padding(vertical = 8.dp)) // Add padding to the Divider
            }
        }
    }
}

@Composable
fun CardItem(
    card: CardUiReference,
    onNavigateCardDetail: (cardId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier= modifier
        .clickable { onNavigateCardDetail(card.cardId) }
        .fillMaxWidth()
        .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        Text(
            text = card.title,
            modifier = Modifier.weight(1.5f),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention))},
        text = { Text(stringResource(R.string.delete_question))},
        modifier = modifier.padding(16.dp),
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no), color = Color.Black)
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes), color = Color.Black)
            }
        }
    )
}