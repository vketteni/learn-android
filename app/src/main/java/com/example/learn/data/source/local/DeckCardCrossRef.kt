package com.example.learn.data.source.local

import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(tableName = "deck_card_cross_ref",
    primaryKeys = ["deckId", "cardId"],
    foreignKeys = [
        ForeignKey(entity = LocalDeck::class, parentColumns = ["deckId"], childColumns = ["deckId"]),
        ForeignKey(entity = LocalCard::class, parentColumns = ["cardId"], childColumns = ["cardId"])
    ]
)
data class DeckCardCrossRef(
    val deckId: String,
    val cardId: String
)
