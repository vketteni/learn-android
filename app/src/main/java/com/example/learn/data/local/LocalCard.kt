package com.example.learn.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "cards")
data class LocalCard(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val deckId: String,
    val frontContent: String,
    val backContent: String,
    val created: Long = System.currentTimeMillis(),
)
