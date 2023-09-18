package com.example.learn.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "cards")
data class LocalCard(
    @PrimaryKey
    val cardId: String = UUID.randomUUID().toString(),
    val content: CardContent,
    val reference: CardReference,
    val created: Long = System.currentTimeMillis(),
)

data class CardContent(
    val front: String,
    val back: String,
)

data class CardReference(
    val position: Int,
    val title: String,
    // Add any additional information here
)