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
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learn.LearnTopBar
import com.example.learn.R
import com.example.learn.data.CardTitle
import com.example.learn.data.local.LocalCard
import com.example.learn.ui.AppViewModelProvider
import kotlinx.coroutines.flow.StateFlow

// Deck composable
@Composable
fun DeckDetailScreen(
    onNavigateUp: () -> Unit,
    onNavigateCardDetail: (cardId: String, deckId: String) -> Unit,
    onNavigateCardAdd: () -> Unit,
    onNavigateDeckSettings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DeckDetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    Scaffold(
        topBar = {
            LearnTopBar(
                title = stringResource(R.string.deck_detail_screen_title), 
                canNavigateBack = true, 
                navigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateCardAdd() },
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

        val deckDetailUiStateFlow: StateFlow<DeckDetailUiState> = viewModel.deckDetailUiState

        val deckDetailUiState: State<DeckDetailUiState> = deckDetailUiStateFlow.collectAsState()

        val currentState: DeckDetailUiState = remember(deckDetailUiState.value) {
            deckDetailUiState.value
        }

        DeckDetailBody(
            modifier = modifier
                .padding(innerPadding),
            cardTitleList = currentState.cardTitleList,
            onNavigateCardDetail = onNavigateCardDetail
        )
    }
}

@Composable
fun DeckDetailBody(
    cardTitleList: List<CardTitle>,
    onNavigateCardDetail: (cardId: String, deckId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (cardTitleList.isEmpty()) {
        Text(
            text = "Your deck has no cards, create a card?",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp) // Add padding to the Text
        )
    } else {
        LazyColumn(modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp) // Add padding to the LazyColumn
        ) {
            items(items=cardTitleList, key = { it.cardId }) { cardTitle ->
                CardItem(cardTitle, onNavigateCardDetail)
                Divider(modifier = Modifier.padding(vertical = 8.dp)) // Add padding to the Divider
            }
        }
    }
}

@Composable
fun CardItem(
    cardTitle: CardTitle,
    onNavigateCardDetail: (cardId: String, deckId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cardId = cardTitle.cardId
    val deckId = cardTitle.deckId
    Row(modifier= modifier
        .clickable { onNavigateCardDetail(cardId, deckId) }
        .fillMaxWidth()
        .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        Text(
            text = cardTitle.title,
            modifier = Modifier.weight(1.5f),
            fontWeight = FontWeight.Bold
        )
    }
}