package com.example.learn.data

import android.content.Context
import com.example.learn.data.local.LearnDatabase

interface AppContainer {
    val decksRepository: DecksRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val decksRepository: DecksRepository by lazy {
        OfflineDecksRepository(LearnDatabase.getDatabase(context).deckDao())
    }


}