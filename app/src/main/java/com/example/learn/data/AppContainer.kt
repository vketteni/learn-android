package com.example.learn.data

import android.content.Context
import com.example.learn.data.source.local.LearnDatabase
import com.example.learn.data.source.network.LearnNetworkDataSource
import com.example.learn.data.source.network.NetworkDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

interface AppContainer {
    val decksRepository: DecksRepository
    val cardsRepository: CardsRepository
    val networkDataSource: NetworkDataSource
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val networkDataSource: NetworkDataSource by lazy {
        LearnNetworkDataSource()
    }

    override val decksRepository: DecksRepository by lazy {
        DefaultDecksRepository(
            LearnDatabase.getDatabase(context).deckDao(),
            LearnDatabase.getDatabase(context).cardDao(),
            LearnDatabase.getDatabase(context).deckCardCrossRefDao(),
            LearnNetworkDataSource(),
            Dispatchers.IO,
            CoroutineScope(Dispatchers.IO)
        )
    }

    override val cardsRepository: CardsRepository by lazy {
        DefaultCardsRepository(
            LearnDatabase.getDatabase(context).cardDao(),
            LearnDatabase.getDatabase(context).deckCardCrossRefDao(),
            LearnNetworkDataSource(),
            Dispatchers.IO,
            CoroutineScope(Dispatchers.IO)
        )
    }

}