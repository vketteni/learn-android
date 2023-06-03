package com.example.learn.data

import android.content.Context
import com.example.learn.data.local.LearnDatabase

interface AppContainer {
    val decksRepository: DecksRepository
    val cardsRepository: CardsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val decksRepository: DecksRepository by lazy {
        OfflineDecksRepository(LearnDatabase.getDatabase(context).deckDao())
    }

    override val cardsRepository: CardsRepository by lazy {
        OfflineCardsRepository(LearnDatabase.getDatabase(context).cardDao())
    }

}