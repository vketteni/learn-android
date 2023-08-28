package com.example.learn.data

import android.content.Context
import com.example.learn.data.local.LearnDatabase
import com.example.learn.data.local.OfflineCardsRepository
import com.example.learn.data.local.OfflineDecksRepository

interface AppContainer {
    val decksRepository: DecksRepository
    val cardsRepository: CardsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val decksRepository: DecksRepository by lazy {
        OfflineDecksRepository(LearnDatabase.getDatabase(context).deckDao())
    }

    override val cardsRepository: CardsRepository by lazy {
        OfflineCardsRepository(LearnDatabase.getDatabase(context).cardDao(), LearnDatabase.getDatabase(context).deckDao())
    }

}