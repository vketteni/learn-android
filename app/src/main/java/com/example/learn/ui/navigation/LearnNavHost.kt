package com.example.learn.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.learn.R
import com.example.learn.SettingsScreen
import com.example.learn.ui.card.CardAddEditScreen
import com.example.learn.ui.card.CardDetailScreen
import com.example.learn.ui.deck.DeckDetailScreen
import com.example.learn.ui.deck.DeckAddEditScreen
import com.example.learn.ui.deck.DeckOverviewScreen

import com.example.learn.ui.navigation.LearnDestinationArguments.CARD_ID_ARG
import com.example.learn.ui.navigation.LearnDestinationArguments.DECK_ID_ARG
import com.example.learn.ui.navigation.LearnDestinationArguments.TITLE_ARG
import com.example.learn.ui.navigation.LearnScreens.CARD_ADD_EDIT_SCREEN
import com.example.learn.ui.navigation.LearnScreens.CARD_DETAIL_SCREEN
import com.example.learn.ui.navigation.LearnScreens.DECK_DETAIL_SCREEN
import com.example.learn.ui.navigation.LearnScreens.DECK_ADD_EDIT_SCREEN
import com.example.learn.ui.navigation.LearnScreens.DECK_OVERVIEW_SCREEN
import com.example.learn.ui.navigation.LearnScreens.SETTINGS_SCREEN


object LearnScreens {
    const val DECK_OVERVIEW_SCREEN = "deck_overview"
    const val DECK_DETAIL_SCREEN = "deck_detail"
    const val DECK_ADD_EDIT_SCREEN = "deck_add_edit"
    const val CARD_DETAIL_SCREEN = "card_detail"
    const val CARD_ADD_EDIT_SCREEN = "card_add_edit"
    const val SETTINGS_SCREEN = "settings"
}

object LearnDestinationArguments {
    const val DECK_ID_ARG = "deck_id"
    const val CARD_ID_ARG = "card_id"
    const val TITLE_ARG = "title"
}

object LearnDestinations {
    const val DECK_OVERVIEW_ROUTE = DECK_OVERVIEW_SCREEN
    const val DECK_ADD_EDIT_ROUTE = "$DECK_ADD_EDIT_SCREEN?$DECK_ID_ARG={$DECK_ID_ARG}"
    const val DECK_DETAIL_ROUTE = "$DECK_DETAIL_SCREEN/{$DECK_ID_ARG}"
    const val CARD_DETAIL_ROUTE = "$CARD_DETAIL_SCREEN/{$DECK_ID_ARG}/{$CARD_ID_ARG}"
    const val CARD_ADD_EDIT_ROUTE = "$CARD_ADD_EDIT_SCREEN/{$DECK_ID_ARG}/{$TITLE_ARG}?$CARD_ID_ARG={$CARD_ID_ARG}"
    const val SETTINGS_ROUTE = SETTINGS_SCREEN
}

@Composable
fun LearnNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = LearnDestinations.DECK_OVERVIEW_ROUTE,
        modifier= modifier
        ) {

        composable(LearnDestinations.DECK_OVERVIEW_ROUTE) {
            DeckOverviewScreen(
                modifier = modifier,
                onNavigateDeckDetail = { deckId ->
                    navController.navigate("$DECK_DETAIL_SCREEN/$deckId") },
                onNavigateDeckEntry = { navController.navigate(DECK_ADD_EDIT_SCREEN) }
            )
        }

        composable(
            route = LearnDestinations.DECK_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(DECK_ID_ARG) { type = NavType.StringType },
            )
        ) { entry ->
            DeckDetailScreen(
                onNavigateCardDetail = { cardId ->
                    navController.navigate("$CARD_DETAIL_SCREEN/${entry.arguments!!.getString(DECK_ID_ARG)}/$cardId") },
                onNavigateAddCard = { navController.navigate("$CARD_ADD_EDIT_SCREEN/${entry.arguments!!.getString(DECK_ID_ARG)}/${R.string.add_card_title}") },
                onNavigateUp = { navController.navigateUp() },
                onDelete = {},
                onNavigateDeckEdit = { deckId ->
                    navController.navigate("$DECK_ADD_EDIT_SCREEN?$DECK_ID_ARG=$deckId")}
            )
        }

        composable(
            route = LearnDestinations.CARD_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(CARD_ID_ARG) { type = NavType.StringType },
                navArgument(DECK_ID_ARG) { type = NavType.StringType },
            )
        ) { entry ->
            CardDetailScreen(
                onNavigateCardEdit = { navController.navigate(
                    "$CARD_ADD_EDIT_SCREEN/{${entry.arguments!!.getString(DECK_ID_ARG)}}/${R.string.edit_card_title}" +
                            "?$CARD_ID_ARG=${entry.arguments!!.getString(CARD_ID_ARG)}"
                )},
                onNavigateUp = { navController.popBackStack(LearnDestinations.DECK_DETAIL_ROUTE, false) },
                onNavigateCardDetail = { cardId: String ->
                    navController.navigate("$CARD_DETAIL_SCREEN/${entry.arguments!!.getString(DECK_ID_ARG)}/$cardId")
                },
                onDeleteCard = { /*TODO*/ },
            )
        }

        composable(LearnDestinations.DECK_ADD_EDIT_ROUTE) {
            DeckAddEditScreen(
                navigateBack = { navController.popBackStack() },
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
                topBarTitle = entry.arguments?.getInt(TITLE_ARG)!!,
                onNavigateUp = { navController.navigateUp() },
                onNavigateBack = { navController.popBackStack() },
            )
        }

        composable(LearnDestinations.SETTINGS_ROUTE) {
            SettingsScreen(
                onNavigateDeckOverviewScreen = { /* TODO */ }
            )
        }

    }
}