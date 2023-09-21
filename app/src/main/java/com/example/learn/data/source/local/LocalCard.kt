package com.example.learn.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "cards")
data class LocalCard(
    @PrimaryKey
    val cardId: String = UUID.randomUUID().toString(),
    val frontContent: String,
    val backContent: String,
    val title: String,
    val position: Int,
    val created: Long = System.currentTimeMillis(),
)