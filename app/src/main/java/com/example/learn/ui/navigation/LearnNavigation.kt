package com.example.learn.ui.navigation

import androidx.navigation.NavHostController
import com.example.learn.ui.navigation.LearnDestinationArguments.CARD_ID_ARG
import com.example.learn.ui.navigation.LearnDestinationArguments.DECK_ID_ARG
import com.example.learn.ui.navigation.LearnDestinationArguments.TITLE_ARG
import com.example.learn.ui.navigation.LearnScreens.CARD_ADD_EDIT_SCREEN
import com.example.learn.ui.navigation.LearnScreens.CARD_DETAIL_SCREEN
import com.example.learn.ui.navigation.LearnScreens.DECK_ADD_EDIT_SCREEN
import com.example.learn.ui.navigation.LearnScreens.DECK_DETAIL_SCREEN
import com.example.learn.ui.navigation.LearnScreens.DECK_OVERVIEW_SCREEN

object LearnScreens {
    const val DECK_OVERVIEW_SCREEN = "deck_overview"
    const val DECK_DETAIL_SCREEN = "deck_detail"
    const val DECK_ADD_EDIT_SCREEN = "deck_add_edit"
    const val CARD_DETAIL_SCREEN = "card_detail"
    const val CARD_ADD_EDIT_SCREEN = "card_add_edit"
}

object LearnDestinationArguments {
    const val DECK_ID_ARG = "deck_id"
    const val CARD_ID_ARG = "card_id"
    const val TITLE_ARG = "title"
}

object LearnDestinations {
    const val DECK_OVERVIEW_ROUTE = DECK_OVERVIEW_SCREEN
    const val DECK_ADD_EDIT_ROUTE = "$DECK_ADD_EDIT_SCREEN/{$TITLE_ARG}?$DECK_ID_ARG={$DECK_ID_ARG}"
    const val DECK_DETAIL_ROUTE = "$DECK_DETAIL_SCREEN/{$DECK_ID_ARG}"
    const val CARD_DETAIL_ROUTE = "$CARD_DETAIL_SCREEN/{$DECK_ID_ARG}/{$CARD_ID_ARG}"
    const val CARD_ADD_EDIT_ROUTE = "$CARD_ADD_EDIT_SCREEN/{$DECK_ID_ARG}/{$TITLE_ARG}?$CARD_ID_ARG={$CARD_ID_ARG}"
}

class LearnNavigationActions(private val navController: NavHostController) {

    fun navigateToDeckDetail(deckId: String) {
        navController.navigate(
            "$DECK_DETAIL_SCREEN/$deckId"
        )
        {
//            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true

        }
    }

    fun navigateToDeckAddEdit(deckId: String?, title: Int) {
        navController.navigate(
            "$DECK_ADD_EDIT_SCREEN/$title".let {
                if (deckId.isNullOrEmpty()) it else "$it?$DECK_ID_ARG=$deckId"
            }
        )
    }

    fun navigateToCardDetail(deckId: String, cardId: String) {
       navController.navigate("$CARD_DETAIL_SCREEN/$deckId/$cardId")
    }

    fun navigateToCardAddEdit(deckId: String, cardId: String?, title: Int) {
        navController.navigate(
            "$CARD_ADD_EDIT_SCREEN/$deckId/$title".let {
                if (cardId != null) "$it?$CARD_ID_ARG=$cardId" else it
            }
        )
    }

    fun navigateToDeckOverview() {
        navController.navigate(DECK_OVERVIEW_SCREEN)
    }
}