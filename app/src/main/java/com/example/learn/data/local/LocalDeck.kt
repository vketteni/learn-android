package com.example.learn.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity (tableName = "decks")
data class LocalDeck(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val created: Long = System.currentTimeMillis(),
)
