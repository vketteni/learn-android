package com.example.learn.ui.deck

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learn.LearnBottomAppBar
import com.example.learn.LearnTopBar
import com.example.learn.R
import com.example.learn.ui.AppViewModelProvider
import com.example.learn.ui.card.CardUiReference
import kotlinx.coroutines.launch

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
    val coroutineScope = rememberCoroutineScope()
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
        bottomBar = {
            LearnBottomAppBar {
                IconButton(onClick = {
                    coroutineScope.launch {
                        onNavigateAddCard(viewModel.deckId)
                    }
                } ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
    ) { innerPadding ->
        DeckDetailBody(
            modifier = modifier
                .fillMaxSize()
                .padding(all = 36.dp)
                .padding(bottom = innerPadding.calculateBottomPadding())
                .padding(top = innerPadding.calculateTopPadding()),
            cards = uiState.cardReferences,
            selectedCard = uiState.selectedCard,
            onNavigateCardDetail = onNavigateCardDetail
        )
    }
}

@Composable
fun DeckDetailBody(
    cards: List<CardUiReference>,
    selectedCard: CardUiReference?,
    onNavigateCardDetail: (cardId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (cards.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.empty_deck_detail_screen_str),
                fontSize = TextUnit(10F, TextUnitType.Em),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
            )
        }
    } else {
        CardsList(
            cards = cards,
            selectedCard = selectedCard,
            onNavigateCardDetail = onNavigateCardDetail,
            modifier = modifier
        )
    }
}

@Composable
fun CardsList(
    cards: List<CardUiReference>,
    selectedCard: CardUiReference?,
    onNavigateCardDetail: (cardId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val sortedCards = cards.sortedBy { it.position }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(items=sortedCards, key = { it.cardId }) { card ->
            CardItem(
                card = card,
                onNavigateCardDetail = onNavigateCardDetail,
                isSelected = selectedCard == card
                )
        }
    }
}

@Composable
fun CardItem(
    card: CardUiReference,
    onNavigateCardDetail: (cardId: String) -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel<DeckDetailViewModel>()
    val borderColor = if (isSelected) Color.Black else Color.Gray
    val backgroundColor = if (isSelected) Color.DarkGray else Color.LightGray
    val contentColor = if (isSelected) Color.White else Color.Black
    val fontSize = 28.sp
    val position = "${card.position + 1}."
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null, // Disable ripple effect
                enabled = true, // Set to true to enable the click
                onClickLabel = null, // Optional label for accessibility
                role = null, // Optional role for accessibility
            ) {
                if (isSelected) onNavigateCardDetail(card.cardId)
                else viewModel.onCardSelect(card)
            },
        colors = CardDefaults.cardColors(),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, borderColor),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Row {
            Text(
                text = card.title,
                //                fontWeight = FontWeight.Bold,
                fontSize = fontSize,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
        }
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