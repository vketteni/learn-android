package com.example.learn.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.learn.SettingsScreen
import com.example.learn.ui.card.CardScreen
import com.example.learn.ui.deck.DeckDetailScreen
import com.example.learn.ui.deck.DeckOverviewScreen

import com.example.learn.ui.navigation.LearnDestinationArguments.CARD_ID_ARG
import com.example.learn.ui.navigation.LearnDestinationArguments.DECK_ID_ARG
import com.example.learn.ui.navigation.LearnScreens.CARD_DETAIL_SCREEN
import com.example.learn.ui.navigation.LearnScreens.DECK_DETAIL_SCREEN
import com.example.learn.ui.navigation.LearnScreens.DECK_OVERVIEW_SCREEN
import com.example.learn.ui.navigation.LearnScreens.SETTINGS_SCREEN


object LearnScreens {
    const val DECK_OVERVIEW_SCREEN = "deck_overview"
    const val DECK_DETAIL_SCREEN = "deck_detail"
    const val CARD_DETAIL_SCREEN = "card_detail"
    const val SETTINGS_SCREEN = "settings"
}

object LearnDestinationArguments {
    const val DECK_ID_ARG = "deck_id"
    const val CARD_ID_ARG = "card_id"
}

object LearnDestinations {
    const val DECK_OVERVIEW_ROUTE = DECK_OVERVIEW_SCREEN
    const val DECK_DETAIL_ROUTE = "$DECK_DETAIL_SCREEN/{$DECK_ID_ARG}"
    const val CARD_DETAIL_ROUTE = "$CARD_DETAIL_SCREEN/{$DECK_ID_ARG}/{$CARD_ID_ARG}"
    const val SETTINGS_ROUTE = SETTINGS_SCREEN
}

@Composable
fun LearnNavGraph(
    modifier: Modifier = Modifier
){
    val navController = rememberNavController()
    val startDestination = LearnDestinations.DECK_OVERVIEW_ROUTE
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier= modifier
        ){
        composable(LearnDestinations.DECK_OVERVIEW_ROUTE) {
            DeckOverviewScreen(
                modifier=modifier,
                onNavigateDeck = { deck -> navController.navigate("$DECK_DETAIL_SCREEN/${deck.id}") },
                onCreateDeck = { /*TODO*/ },
                onUpdateDeck = { /*TODO*/ },
            )
        }
        composable(
            route = LearnDestinations.DECK_DETAIL_ROUTE,
            arguments = listOf(navArgument(DECK_ID_ARG) { type = NavType.StringType })
        ) {
            DeckDetailScreen(
                onNavigateCard = { card -> navController.navigate("$CARD_DETAIL_SCREEN/${card.deckId}/${card.id}") },
                onCreateCard = { /*TODO*/ },
                onDeleteDeck = { /*TODO*/ }
            )
        }
        composable(
            route = LearnDestinations.CARD_DETAIL_ROUTE,
            arguments = listOf(navArgument(CARD_ID_ARG) { type = NavType.StringType })
        ) {
            CardScreen(
                onUpdateContent = { /*TODO*/ },
                onSwitchSide = { /*TODO*/ },
                onDeleteCard = { /*TODO*/ })
        }
        composable(LearnDestinations.SETTINGS_ROUTE) {
            SettingsScreen(
                onNavigateDeckOverviewScreen = { /* TODO */ }
            )
        }
    }
}