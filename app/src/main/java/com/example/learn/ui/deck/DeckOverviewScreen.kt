package com.example.learn.ui.deck

import Deck
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learn.DeckOverviewTopBar
import com.example.learn.LearnBottomAppBar
import com.example.learn.ui.AppViewModelProvider
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Deck composable
@Composable
fun DeckOverviewScreen(
    onNavigateDeckDetail: (deckId: String) -> Unit,
    onNavigateDeckAddEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DeckOverviewViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = { DeckOverviewTopBar(onRefresh = { viewModel.refresh() })},
        bottomBar = { LearnBottomAppBar {
            IconButton(onClick = {
                coroutineScope.launch {
                    onNavigateDeckAddEdit()
                }
            } ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } }
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
        Column(modifier = modifier) {
            SlideScreenWithIndicator(deckList, onNavigateDeckDetail)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SlideScreenWithIndicator(
    slides: List<Deck>,
    onNavigateDeckDetail: (deckId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { slides.size })
    HorizontalPager(
        modifier = Modifier
            .fillMaxHeight(0.95f),
        state = pagerState
    ) { page ->
        Row(Modifier.clickable { onNavigateDeckDetail(slides[page].deckId) }) {
            SlideContent(slides[page])
        }
    }
    DotIndicatorRow(slides.size, pagerState.currentPage)
}

@Composable
fun DotIndicatorRow(length: Int, currentIndicator: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(length) { index ->
            DotIndicator(isSelected = index == currentIndicator)
        }
    }
}

@Composable
private fun SlideContent(
    deck: Deck,
    modifier: Modifier = Modifier
) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = Color(1)),
            elevation = CardDefaults.cardElevation(),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = deck.title,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
}

@Composable
fun DotIndicator(isSelected: Boolean) {
    val color = if (isSelected) Color.Blue else Color.Gray
    Box(
        modifier = Modifier
            .size(12.dp)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(12.dp)) {
            drawCircle(
                color = color,
                radius = size.minDimension / 2
            )
        }
    }
}
