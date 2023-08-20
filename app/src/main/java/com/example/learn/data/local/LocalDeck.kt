package com.example.learn.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity (tableName = "decks")
data class LocalDeck(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val cardReferences: List<CardReference> = listOf(),
    val created: Long = System.currentTimeMillis(),
)

data class CardReference(
    val cardId: String,
    val deckId: String,
    val position: Int,
    val title: String,
    // Add any additional information here
)