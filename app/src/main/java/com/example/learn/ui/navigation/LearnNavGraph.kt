package com.example.learn.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.learn.R
import com.example.learn.ui.card.CardAddEditScreen
import com.example.learn.ui.card.CardDetailScreen
import com.example.learn.ui.deck.DeckDetailScreen
import com.example.learn.ui.deck.DeckAddEditScreen
import com.example.learn.ui.deck.DeckOverviewScreen

import com.example.learn.ui.navigation.LearnDestinationArguments.CARD_ID_ARG
import com.example.learn.ui.navigation.LearnDestinationArguments.DECK_ID_ARG
import com.example.learn.ui.navigation.LearnDestinationArguments.TITLE_ARG

@Composable
fun LearnNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = LearnDestinations.DECK_OVERVIEW_ROUTE,
    navActions: LearnNavigationActions = remember(navController) {
        LearnNavigationActions(navController)
    }
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier= modifier
        ) {

        composable(LearnDestinations.DECK_OVERVIEW_ROUTE) {
            DeckOverviewScreen(
                modifier = modifier,
                onNavigateDeckDetail = { deckId ->
                    navActions.navigateToDeckDetail(deckId)
                },
                onNavigateDeckAddEdit = { navActions.navigateToDeckAddEdit(null, R.string.deck_add_title) }
            )
        }

        composable(
            route = LearnDestinations.DECK_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(DECK_ID_ARG) { type = NavType.StringType },
            )
        ) { entry ->
            val deckId = entry.arguments?.getString(DECK_ID_ARG)!!
            DeckDetailScreen(
                onNavigateCardDetail = { cardId ->
                    navActions.navigateToCardDetail(deckId, cardId) },
                onNavigateAddCard = { navActions.navigateToCardAddEdit(deckId = deckId, cardId = null, title = R.string.add_card_title) },
                onNavigateUp = { navController.popBackStack(LearnDestinations.DECK_OVERVIEW_ROUTE, false) },
                onDelete = { navActions.navigateToDeckOverview() },
                onNavigateDeckEdit = { navActions.navigateToDeckAddEdit(deckId, R.string.deck_edit_title) }
            )
        }

        composable(
            route = LearnDestinations.CARD_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(CARD_ID_ARG) { type = NavType.StringType },
                navArgument(DECK_ID_ARG) { type = NavType.StringType },
            )
        ) { entry ->
            val deckId = entry.arguments?.getString(DECK_ID_ARG)!!
            val cardId = entry.arguments?.getString(CARD_ID_ARG)!!
            CardDetailScreen(
                onNavigateCardEdit = { navActions.navigateToCardAddEdit(deckId, cardId, R.string.edit_card_title) },
                onNavigateUp = { navController.popBackStack(LearnDestinations.DECK_DETAIL_ROUTE, false) },
                onNavigateCardDetail = { navActions.navigateToCardDetail(deckId, cardId) },
                onNavigateDeckDetail = { navActions.navigateToDeckDetail(deckId) }
            )
        }

        composable(
            route = LearnDestinations.DECK_ADD_EDIT_ROUTE,
            arguments = listOf(
                navArgument(TITLE_ARG) { type = NavType.IntType }
            )
        ) { entry ->
            val title = entry.arguments?.getInt(TITLE_ARG)!!
            DeckAddEditScreen(
                title = title,
                navigateDeckOverview = { navActions.navigateToDeckOverview() },
                navigateUp = { navController.navigateUp() }
            )
        }

        composable(
            route = LearnDestinations.CARD_ADD_EDIT_ROUTE,
            arguments = listOf(
                navArgument(TITLE_ARG) { type = NavType.IntType },
                navArgument(DECK_ID_ARG) { type = NavType.StringType },
                navArgument(CARD_ID_ARG) { type = NavType.StringType; nullable = true },
            )
        ) { entry ->

            CardAddEditScreen(
                title = entry.arguments?.getInt(TITLE_ARG)!!,
                onNavigateUp = { navController.navigateUp() },
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}